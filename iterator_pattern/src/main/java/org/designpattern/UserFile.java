package org.designpattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class UserFile implements Iterable<User> {
    private final File file;

    public UserFile(File file) {
        this.file = file;
    }

    @Override
    public Iterator<User> iterator() {
        return new UserFileIter();
    }

    private class UserFileIter implements Iterator<User> {
        private BufferedReader reader;
        private String nextLine;
        private boolean isInitialized;
        private UserFileIter() {
            try {
                reader = Files.newBufferedReader(file.toPath());
                isInitialized = false;
                advance();
            }catch (IOException e) {
                throw new RuntimeException("无法打开文件" + file.getPath(), e);
            }
        }
       private void advance() {
            try {
                nextLine = reader.readLine();
                isInitialized = true;
            }catch (IOException e) {
                nextLine = null;
                closeReader();
                throw new RuntimeException("读取文件" + file.getPath() + "失败", e);
            }
       }
        @Override
        public boolean hasNext() {
          if(!isInitialized) {
              advance();
          }
          return nextLine != null;
        }

        @Override
        public User next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            try {
                String line = nextLine;
                advance(); // 读取下一行
                String[] split = line.substring(1, line.length() - 1).split(",");
                return new User(split[0].trim(), Integer.parseInt(split[1].trim()));
            } catch (Exception e) {
                closeReader();
                throw new RuntimeException("解析行出错: " + nextLine, e);
            }

        }
        private void closeReader() {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 可记录日志或处理错误
                }
                reader = null;
            }
        }


    }
}
