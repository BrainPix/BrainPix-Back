package com.brainpix.profile;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.brainpix.profile.dto.request.PortfolioRequest;
import com.brainpix.profile.dto.request.SpecializationRequest;
import com.brainpix.profile.dto.response.PortfolioDetailResponse;
import com.brainpix.profile.dto.response.PortfolioResponse;
import com.brainpix.profile.entity.IndividualProfile;
import com.brainpix.profile.entity.Profile;
import com.brainpix.profile.repository.ProfileRepository;
import com.brainpix.profile.repository.UserRepository;
import com.brainpix.profile.service.PortfolioService;
import com.brainpix.user.entity.Company;
import com.brainpix.user.entity.Individual;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class PortfolioServiceTest {

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Test
	@DisplayName("포트폴리오 여러 개 생성 후 페이지네이션 조회 테스트")
	void createMultiplePortfoliosAndFindWithPagination() {
		// given
		Individual user = createIndividualUser("testUser");
		userRepository.save(user);

		// 포트폴리오 15개 생성
		for (int i = 1; i <= 15; i++) {
			PortfolioRequest request = new PortfolioRequest(
				"포트폴리오 제목 " + i,
				List.of(new SpecializationRequest("IT_TECH")),
				YearMonth.of(2023, i % 12 + 1),
				YearMonth.of(2024, i % 12 + 1),
				"포트폴리오 내용 " + i
			);
			portfolioService.createPortfolio(user.getId(), request);
		}

		// when - 페이지네이션 조회 (페이지 크기: 5)
		PageRequest pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
		Page<PortfolioResponse> portfoliosPage = portfolioService.findAllMyPortfolios(user.getId(), pageable);

		// then - 페이지네이션 검증
		assertThat(portfoliosPage).isNotNull();
		assertThat(portfoliosPage.getContent()).hasSize(5); // 페이지 크기: 5
		assertThat(portfoliosPage.getTotalElements()).isEqualTo(15); // 전체 포트폴리오 개수
		assertThat(portfoliosPage.getTotalPages()).isEqualTo(3); // 전체 페이지 수: 15 / 5 = 3
		assertThat(portfoliosPage.getNumber()).isEqualTo(0); // 현재 페이지 번호

		// 첫 번째 포트폴리오 검증 (최신순)
		PortfolioResponse firstPortfolio = portfoliosPage.getContent().get(0);
		assertThat(firstPortfolio.title()).isEqualTo("포트폴리오 제목 15");

		// 두 번째 페이지 조회
		PageRequest secondPageable = PageRequest.of(1, 5, Sort.by("createdAt").descending());
		Page<PortfolioResponse> secondPage = portfolioService.findAllMyPortfolios(user.getId(), secondPageable);

		// 두 번째 페이지 검증
		assertThat(secondPage).isNotNull();
		assertThat(secondPage.getContent()).hasSize(5);
		assertThat(secondPage.getTotalElements()).isEqualTo(15);
		assertThat(secondPage.getTotalPages()).isEqualTo(3);
		assertThat(secondPage.getNumber()).isEqualTo(1);

		// 두 번째 페이지의 첫 번째 포트폴리오 검증
		PortfolioResponse secondPageFirstPortfolio = secondPage.getContent().get(0);
		assertThat(secondPageFirstPortfolio.title()).isEqualTo("포트폴리오 제목 10");
	}

	@Test
	@DisplayName("포트폴리오 수정 테스트")
	void updatePortfolio() {

		Company company = createCompanyUser("testCompany");
		userRepository.save(company);

		PortfolioRequest request = new PortfolioRequest(
			"회사 포트폴리오",
			List.of(new SpecializationRequest("MARKETING")),
			YearMonth.of(2022, 5),
			YearMonth.of(2022, 10),
			"처음 내용"
		);
		Long portfolioId = portfolioService.createPortfolio(company.getId(), request);

		PortfolioRequest updateRequest = new PortfolioRequest(
			"회사 포트폴리오 (수정)",
			List.of(new SpecializationRequest("ADVERTISING_PROMOTION"), new SpecializationRequest("IT_TECH")),
			YearMonth.of(2022, 6),
			YearMonth.of(2022, 12),
			"수정된 내용"
		);

		portfolioService.updatePortfolio(company.getId(), portfolioId, updateRequest);

		PortfolioDetailResponse detail = portfolioService.findPortfolioDetail(company.getId(), portfolioId);
		assertThat(detail.title()).isEqualTo("회사 포트폴리오 (수정)");
		assertThat(detail.content()).isEqualTo("수정된 내용");
		assertThat(detail.specializations()).containsExactlyInAnyOrder("ADVERTISING_PROMOTION", "IT_TECH");
		assertThat(detail.startDate()).isEqualTo("2022-06");
		assertThat(detail.endDate()).isEqualTo("2022-12");
	}

	private Individual createIndividualUser(String identifier) {
		Profile profile = createProfile();
		return new Individual(
			identifier,
			"pw1234",
			"테스터",
			LocalDateTime.of(1990, 1, 1, 0, 0),
			identifier + "@test.com",
			null,  // profileImage
			profile
		);
	}

	private Company createCompanyUser(String identifier) {
		Profile profile = createProfile();
		return new Company(
			identifier,
			"pw1234",
			"회사명",
			LocalDateTime.of(2000, 1, 1, 0, 0),
			identifier + "@test.com",
			null,
			profile,
			"TestCompany",
			"CEO"
		);
	}

	private Profile createProfile() {
		IndividualProfile individualProfile = new IndividualProfile(
			List.of(), // specialization list
			null,      // selfIntroduction
			false,     // contactOpen
			false,     // careerOpen
			false      // stackOpen
		);
		return profileRepository.save(individualProfile);
	}

}
