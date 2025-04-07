package sky.pro.Hogwarts31Test.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.Hogwarts31Test.model.Avatar;
import sky.pro.Hogwarts31Test.model.dto.AvatarView;
import sky.pro.Hogwarts31Test.service.AvatarService;

import java.io.IOException;

@RequestMapping("/avatar")
@RestController
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;

    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public long uploadAvatar(@RequestParam("studentId") long studentId,
                             @RequestBody MultipartFile file) throws IOException {
        return avatarService.uploadAvatar(studentId, file);
    }

    @GetMapping("/get/from-db")
    public ResponseEntity<byte[]> getAvatarFromDb(@RequestParam("studentId") long studentId) {
        Avatar avatar = avatarService.getAvatarFromDb(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());
    }

    @GetMapping("/get/from-local")
    public ResponseEntity<byte[]> getAvatarFromLocal(@RequestParam("studentId") long studentId) throws IOException {
        AvatarView view = avatarService.getAvatarFromLocal(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(view.getMediaType())
                .body(view.getContent());
    }

    /**
     * Получение списка аватаров с пагинацией
     *
     * @param pageNumber Номер страницы
     * @param pageSize   Размер страницы
     * @return ResponseEntity с Page&lt;Avatar&gt;- содержит список аватарок и информацию о пагинации.
     * Возвращает 200 OK с данными или 500 в случае ошибки.
     */

    @GetMapping("/pageable")
    public ResponseEntity<Page<Avatar>> getAllAvatar(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(avatarService.getAllAvatar(pageable));
    }
}
