package sky.pro.Hogwarts31Test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sky.pro.Hogwarts31Test.model.Avatar;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.model.dto.AvatarView;
import sky.pro.Hogwarts31Test.model.exception.AvatarNotFoundException;
import sky.pro.Hogwarts31Test.model.exception.StudentNotFoundException;
import sky.pro.Hogwarts31Test.repository.AvatarRepository;
import sky.pro.Hogwarts31Test.repository.StudentRepository;
import sky.pro.Hogwarts31Test.service.AvatarService;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Transactional
@Service
public class AvatarServiceImpl implements AvatarService {
    @Value("${image.path}")
    private final Path pathDir;

    private final AvatarRepository avatarRepository;

    private final StudentRepository studentRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository,
                             StudentRepository studentRepository,
                             @Value("${image.path") Path pathDir) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.pathDir = pathDir;
    }


    @Override
    public long uploadAvatar(long studentId, MultipartFile file) throws IOException {
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new StudentNotFoundException(studentId));
        Path path = saveAvatarLocal(file);

        // System.out.println(path.toString());

        Avatar avatar = new Avatar(
                path.toString(),
                file.getSize(),
                file.getContentType(),
                file.getBytes(),
                student

        );
        //Optional<Avatar> savedAvatar = avatarRepository.findByStudentId(studentId);
        //ifPresent((x) -> {
        //   Files.delete((Path.of(x.getFilePath())));
        //   avatar.setId(x.getId());
        // });


        // Avatar savedAvatar = avatarRepository.findByStudentId(studentId);
        //if (savedAvatar != null) {
        avatarRepository.findByStudentId(studentId)

                //Files.delete((Path.of(savedAvatar.getFilePath())));
                //avatar.setId(savedAvatar.getId());

                .ifPresent((x) -> {
                    try {
                        Files.delete(Path.of(x.getFilePath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    avatar.setId((x.getId()));
                });

        avatarRepository.save(avatar);
        return avatar.getId();
        //return avatarRepository.save(avatar).getId(); то же самое по другому
    }

    private Path saveAvatarLocal(MultipartFile file) throws IOException {
        createDirectoryIfNotExist();

        if (file.getOriginalFilename() == null) {
            throw new RuntimeException("не корректное имя изображения");
        }

        Path path = Path.of(pathDir.toString(), UUID.randomUUID() + getExstension(file.getOriginalFilename()));

        file.transferTo(path);

        return path;
    }

    private String getExstension(String path) {
        return path.substring(path.lastIndexOf("."));
        // ищем последнюю точку (расширение ggggg.jpg) и обрезаем всё, что после неё
    }

    private void createDirectoryIfNotExist() throws IOException {
        System.out.println(pathDir);
        if (Files.notExists(pathDir)) {
            Files.createDirectory(pathDir);
        }
    }

    @Override
    public Avatar getAvatarFromDb(long studentId) {

        return avatarRepository.findByStudentId(studentId)
                .orElseThrow(AvatarNotFoundException::new);

    }

    @Override
    public AvatarView getAvatarFromLocal(long studentId) throws IOException {
        Avatar avatar = avatarRepository.findByStudentId((studentId))
                .orElseThrow(AvatarNotFoundException::new);
        byte[] bytes = Files.readAllBytes(Path.of(avatar.getFilePath()));
        return new AvatarView(MediaType.parseMediaType(avatar.getMediaType()), bytes);

    }
}

