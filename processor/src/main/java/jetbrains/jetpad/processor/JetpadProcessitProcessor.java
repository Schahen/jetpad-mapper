package jetbrains.jetpad.processor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.shabunc.maven.Processor;

import java.io.File;
import java.util.Iterator;

public class JetpadProcessitProcessor implements Processor {

  @Override
  public Iterator<File> getFiles() {
    File dir = new File("src/main/java");

    String[] extensions = new String[] {"jetpad.xml"};
    IOFileFilter filter = new SuffixFileFilter(extensions, IOCase.SENSITIVE);
    Iterator iter = FileUtils.iterateFiles(dir, filter, TrueFileFilter.INSTANCE);
    return iter;
  }

  @Override
  public void process(File file) {
    System.out.println("========= PROCESSING ======= " + file.getName());
  }

}
