package com.smartcity.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartcity.message.ResponseFile;
import com.smartcity.message.ResponseMessage;
import com.smartcity.model.FileDB;
import com.smartcity.model.User;
import com.smartcity.model.UserData;
import com.smartcity.repository.FileDBRepository;
import com.smartcity.repository.UserRepository;
import com.smartcity.service.FileStorageService;

@Controller
public class FileController {

  @Autowired
  private FileStorageService storageService;
  @Autowired
private UserRepository userRepository;
  @Autowired
  private FileDBRepository fileDBRepository;

  @PostMapping("upload")
  public String uploadFile(@RequestParam("file") MultipartFile file, User user) {
    String message = "";
	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 String username = ((UserDetails)principal).getUsername();
	 user = this.userRepository.findByEmail(username);
    try {
      storageService.store(file,user);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
   //   return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
      return "redirect:fileN?success";
  }
  @GetMapping("/fileUploaded")
  public String fileUploaded(Model model, User user) {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String username = ((UserDetails)principal).getUsername();
		 user = this.userRepository.findByEmail(username);
		 model.addAttribute("user", user);
  	return "file-uploaded";
  }
  
@GetMapping("/fileN")
public String file(Model model, User user) {
	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 String username = ((UserDetails)principal).getUsername();
	 user = this.userRepository.findByEmail(username);
     FileDB userFile = this.fileDBRepository.findByUser(user);
	 model.addAttribute("file", userFile);
	return "file";
}

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
  
}