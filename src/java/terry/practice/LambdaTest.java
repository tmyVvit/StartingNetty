package terry.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LambdaTest {
    public static void main(String[] args) {
        String[] data = {"10.70.44.68~255.254.255.0",
                "1.0.0.1~255.0.0.0",
                "192.168.0.2~255.255.255.0",
                "19..0.~255.255.255.0"
        };
        int[] res = new int[7];
        for (String line : data) {
            if (line.contains("*")) continue;
            String[] ip = line.split("~");
            if (!isValidMask(ip[1]) || !isValidIp(ip[0])) {
                res[5]++;
                continue;
            }
            char type = getIpType(ip[0]);
            res[type - 'A']++;
            if (isPrivate(ip[0])) {
                res[6]++;
            }
        }
        for (int count : res) {
            System.out.print(count + " ");
        }

    }

    private static boolean isValidIp(String ip) {
        String[] ips = ip.split("\\.");
        if (ips.length != 4) return false;
        for (String addr : ips) {
            if (addr == null || addr.length() == 0) return false;
        }
        return true;
    }

    private static boolean isValidMask(String mask) {
        String[] masks = mask.split("\\.");
        if (masks.length != 4) return false;
        if ("0".equals(masks[0]) && "0".equals(masks[1]) && "0".equals(masks[2]) && "0".equals(masks[3])) return false;
        if ("255".equals(masks[0]) && "255".equals(masks[1]) && "255".equals(masks[2]) && "255".equals(masks[3]))
            return false;

        boolean hasZero = false;
        for (String str : masks) {
            if (hasZero && !"0".equals(str)) return false;
            if (hasZero) continue;;
            int num = Integer.parseInt(str);
            int base = 0;
            while (base < 8) {
                if (((num >> base) & 1) == 1) {
                    if ((num >> base) != ((1 << ((8-base)))-1)) return false;
                    break;
                } else {
                    hasZero = true;
                }
                base++;
            }
        }
        return true;
    }

    private static char getIpType(String ip) {
        String first = ip.split("\\.")[0];
        int num = Integer.parseInt(first);
        if (num >= 1 && num <= 126) return 'A';
        if (num >= 128 && num <= 191) return 'B';
        if (num >= 192 && num <= 223) return 'C';
        if (num >= 224 && num <= 239) return 'D';
        if (num >= 240 && num <= 255) return 'E';
        return ' ';
    }

    private static boolean isPrivate(String ip) {
        String[] addrs = ip.split("\\.");
        if ("10".equals(addrs[0])) return true;
        if ("172".equals(addrs[0])) {
            int second = Integer.parseInt(addrs[1]);
            return second >= 16 && second <= 31;
        }
        if ("192".equals(addrs[0]) && "168".equals(addrs[1])) return true;
        return false;
    }
}
