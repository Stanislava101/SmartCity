package com.smartcity.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.smartcity.model.FileDB;
import com.smartcity.model.User;
import com.smartcity.repository.FileDBRepository;


@Service
public class FileStorageService {

  @Autowired
  private FileDBRepository fileDBRepository;

  public void store(MultipartFile file,User user) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(),user);
   // return fileDBRepository.save(FileDB);
    fileDBRepository.save(FileDB);
  //  fileDBRepository.save(new FileDB(fileName, file.getContentType(), file.getBytes(),user));
  }

  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
	public Long getUserID(User user) {
		return user.getId();
	}

}