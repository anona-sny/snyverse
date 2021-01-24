package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.TagEntity;
import cz.anona.snyverse.entities.UserEntity;
import cz.anona.snyverse.entities.UserLoginEntity;
import cz.anona.snyverse.entities.UserSettingsEntity;
import cz.anona.snyverse.entities.enums.CountryCode;
import cz.anona.snyverse.entities.enums.LanguageCode;
import cz.anona.snyverse.entities.enums.UserType;
import cz.anona.snyverse.repositories.UserLoginRepository;
import cz.anona.snyverse.repositories.UserRepository;
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
public class StartupService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserService userService;
    private final TagService tagService;

    public StartupService(UserRepository userRepository, UserLoginRepository userLoginRepository,
                          UserService userService, TagService tagService) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.userService = userService;
        this.tagService = tagService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loaders() throws Exception {
        this.loadAdmin();
        this.loadTags();
    }

    public void loadAdmin() {
        if(this.userLoginRepository.getByUsername("administrator") == null) {
            UserEntity administrator = new UserEntity();
            administrator.setName("Administrator");
            administrator.setCountry(CountryCode.CZE);
            administrator.setLanguage(LanguageCode.CZE);
            administrator.setType(UserType.ADMINISTRATOR);
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
        }
    }

}
