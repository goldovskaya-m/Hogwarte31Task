package sky.pro.Hogwarts31Test.controller;

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

        //return avatarService.getAvatarFromDb(studentId);
    }

    @GetMapping("/get/from-local")
    public ResponseEntity<byte[]> getAvatarFromLocal(@RequestParam("studentId") long studentId) throws IOException {
        AvatarView view = avatarService.getAvatarFromLocal(studentId);
        //byte[] bytes = Files.readAllBytes(Path.of(avatar.getFilePath()));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(view.getMediaType())
                .body(view.getContent());

        // .contentType(MediaType.parseMediaType(avatar.getMediaType()))
        // .body(bytes);


        //public byte[] getAvatarFromLocal(@RequestParam("studentId") long studentId) throws IOException {
        //    return avatarService.getAvatarFromLocal(studentId);
    }
}
