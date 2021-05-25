package ru.share.Handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
@AllArgsConstructor
public class FileListMessage implements Serializable {
    @Getter
    private List<String> files;
}
