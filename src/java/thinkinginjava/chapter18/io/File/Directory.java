package thinkinginjava.chapter18.io.File;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

//
public class Directory {
    public static File[] local(File dir, final String regex) {
        return dir.listFiles(new FilenameFilter() {
            private final Pattern pattern = Pattern.compile(regex);
            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(new File(name).getName()).matches();
            }
        });
    }

    public static File[] local(String path, final String regex) {
        return local(new File(path), regex);
    }

    public static TreeInfo walk(String start, String regex) {
        return recurseDirs(new File(start), regex);
    }

    public static TreeInfo walk(File start, String regex) {
        return recurseDirs(start, regex);
    }

    public static TreeInfo walk(File start) {
        return recurseDirs(start, ".*");
    }

    public static TreeInfo walk(String start) {
        return recurseDirs(new File(start), ".*");
    }

    public static TreeInfo recurseDirs(File dir, String regex) {
        TreeInfo result = new TreeInfo();
        for (File item : dir.listFiles()) {
            if (item.isDirectory()) {
                result.dirs.add(item);
                result.addAll(recurseDirs(item, regex));
            } else {
                if (item.getName().matches(regex))
                    result.files.add(item);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(walk("."));
        } else {
            for (String arg : args) {
                System.out.println(walk(arg));
            }
        }
    }

    public static class TreeInfo implements Iterable<File> {
        public List<File> files = new ArrayList<>();
        public List<File> dirs = new ArrayList<>();

        @NotNull
        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo other) {
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

        public String listFiles(List<File> f) {
            StringBuilder sb = new StringBuilder();
            for (File file : f) {
                if (sb.length() > 0) {
                    sb.append(";");
                }
                sb.append(file.getName());
            }
            return sb.toString();
        }

        public String toString() {
            return "dirs: " + listFiles(dirs) + "\nfiles: " + listFiles(files);
        }
    }
}
