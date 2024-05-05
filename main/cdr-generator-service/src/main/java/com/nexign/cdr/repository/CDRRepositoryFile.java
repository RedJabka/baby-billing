package com.nexign.cdr.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.nexign.cdr.entity.CDR;

public interface CDRRepositoryFile {
    File saveToFile(List<CDR> cdr) throws IOException;
}
