public class StringBenchmark {
    static String withPlus(String[] fields) {
        String s = "";
        for (String f : fields) s += f + "|";
        return s;
    }

    static String withBuilder(String[] fields) {
        StringBuilder b = new StringBuilder();
        for (String f : fields) b.append(f).append('|');
        return b.toString();
    }

    static String withBuffer(String[] fields) {
        StringBuffer b = new StringBuffer();
        for (String f : fields) b.append(f).append('|');
        return b.toString();
    }

    static long time(Runnable task, int reps) {
        long start = System.nanoTime();
        for (int i = 0; i < reps; i++) task.run();
        return (System.nanoTime() - start) / reps;
    }

    public static void main(String[] args) {
        int[][] workloads = {{6, 1}, {50, 10000}, {120, 30000}};
        for (int[] w : workloads) {
            String[] fields = new String[w[0]];
            for (int i = 0; i < fields.length; i++) fields[i] = "FIELD" + i + "-VALUE";
            int reps = w[1];
            for (int i = 0; i < 100; i++) {
                withPlus(fields); withBuilder(fields); withBuffer(fields);
            }
            long plus = time(() -> withPlus(fields), reps);
            long builder = time(() -> withBuilder(fields), reps);
            long buffer = time(() -> withBuffer(fields), reps);
            System.out.printf("fields=%d reps=%d plus=%d ns builder=%d ns buffer=%d ns%n",
                    w[0], reps, plus, builder, buffer);
        }
        String[] ticket = {"PNR:1234567890", "Name:ArunKumar", "Coach:S2",
                "Berth:31", "Fare:850", "Route:Chennai to Bengaluru"};
        System.out.println("Sample ticket: " + withBuilder(ticket));
    }
}
