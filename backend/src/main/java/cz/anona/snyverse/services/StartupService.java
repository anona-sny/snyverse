package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.*;
import cz.anona.snyverse.entities.enums.CountryCode;
import cz.anona.snyverse.entities.enums.LanguageCode;
import cz.anona.snyverse.entities.enums.UserType;
import cz.anona.snyverse.entities.enums.VisibilityType;
import cz.anona.snyverse.repositories.TagRepository;
import cz.anona.snyverse.repositories.UserLoginRepository;
import cz.anona.snyverse.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StartupService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserService userService;
    private final TagService tagService;
    private final CompanyService companyService;

    public StartupService(UserRepository userRepository, UserLoginRepository userLoginRepository,
                          UserService userService, TagService tagService, CompanyService c1) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.userService = userService;
        this.tagService = tagService;
        this.companyService = c1;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loaders() throws Exception {
        this.loadAdmin();
        this.loadTags();
        this.loadMostKnownCompanies();
    }

    public void loadAdmin() {
        if(this.userLoginRepository.getByUsername("administrator") == null) {
            log.info("Creating administrator");
            UserEntity administrator = new UserEntity();
            administrator.setName("Administrator");
            administrator.setCountry(CountryCode.CZE);
            administrator.setLanguage(LanguageCode.CZE);
            administrator.setType(UserType.ADMINISTRATOR);
            administrator.setVisibility(VisibilityType.PUBLIC);
            UserLoginEntity userLoginEntity = new UserLoginEntity();
            userLoginEntity.setUser(administrator);
            userLoginEntity.setUsername("administrator");
            userLoginEntity.setEmail("frantisekzavazal@seznam.cz");
            userLoginEntity.setPasswordHash("79da970551b80e3c6c0c9768879a439fec0d60c812f2b3f198ed634aa62e4c7740d8fd" +
                    "2f0c49a6de2cf9e79e9ab6705724ec05eba1dc34eb633660ada8480089");  // fsociety
            administrator.setLoginEntity(userLoginEntity);
            UserSettingsEntity userSettingsEntity = new UserSettingsEntity();
            userSettingsEntity.setFontSize(UserSettingsEntity.FONT_SIZE);
            userSettingsEntity.setSchema("DEFAULT");
            userSettingsEntity.setUser(administrator);
            administrator.setSettingsEntity(userSettingsEntity);
            this.userRepository.save(administrator);
            log.info("Administrator created");
        }
    }

    /**
     * Loading all tags when start up first time
     * @throws Exception file not found, database contains or other errors
     */
    public void loadTags() throws Exception {

        UserEntity admin = this.userService.getUserByUsername("administrator");
        Resource file = new ClassPathResource("tags-list.dat");

        List<String> lines = new ArrayList<>();

        if(file.exists() && file.isReadable()) {
            log.info("Loading tags");
            Scanner reader = new Scanner(file.getFile());
            while(reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
            List<String> tags = this.tagService.listAllTags().stream().map(TagEntity::getName).collect(Collectors.toList());
            lines = lines.stream().filter(s -> !tags.contains(s.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
            for(String tag: lines) {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setName(tag);
                tagEntity.setAuthor(admin);
                this.tagService.createTag(tagEntity);
            }
            log.info(lines.size() + " tags created");
        }
    }

    public void loadMostKnownCompanies() {
        List<CompanyEntity> companyEntities = new ArrayList<>();
        companyEntities.add(CompanyEntity.of("ČEZ", CountryCode.CZE));
        companyEntities.add(CompanyEntity.of("Škoda auto", CountryCode.CZE));
        companyEntities.add(CompanyEntity.of("Walmart", CountryCode.USA));
        companyEntities.add(CompanyEntity.of("Volkswagen", CountryCode.DEU));
        companyEntities.add(CompanyEntity.of("Amazon", CountryCode.USA));
        companyEntities.add(CompanyEntity.of("Sinopec Group", CountryCode.CHN));
        companyEntities.add(CompanyEntity.of("BMW", CountryCode.DEU));
        companyEntities.add(CompanyEntity.of("Maersk", CountryCode.DNK));
        companyEntities.add(CompanyEntity.of("Sodexo", CountryCode.FRA));
        companyEntities.add(CompanyEntity.of("Airbus SE", CountryCode.NLD));
        companyEntities.add(CompanyEntity.of("Nokia", CountryCode.FIN));
        companyEntities.add(CompanyEntity.of("Volvo", CountryCode.SWE));
        for(CompanyEntity ce: companyEntities) {
            if(!this.companyService.companyExist(ce.getName())) {
                this.companyService.addCompany(ce);
            }
        }
    }

}
