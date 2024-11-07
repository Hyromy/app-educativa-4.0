package com.example.app.utils;

public class Teacher {
    public int knowledgeLevel(int levels, int questions, int avgMaxScorePerAnswer, int studentScore) {
        float R[] = multiplyRanges(levels);
        int S[] = scoreSections(R, avgMaxScorePerAnswer, questions);

        return getLevel(S, studentScore, levels);
    }

    public float[] multiplyRanges(int levels) {
        int numerator;
        int denominator = (int) (Math.pow(2, levels) -1);
        float[] R = new float[levels -1];
        for (int i = 0; i < R.length; i++) {
            numerator = 0;
            for (int j = 1 - levels; j < -i; j++) {
                numerator += (int) Math.pow(2, Math.abs(j));
            }
            R[i] = (float) numerator / (float) denominator;
        }

        return R;
    }

    public int[] scoreSections(float[] R, int avgMaxScorePerAnswer, int questions) {
        int[] S = new int[R.length];
        for (int i = 0; i < S.length; i++) {
            S[i] = Math.round(R[i] * avgMaxScorePerAnswer * questions);
        }
        return S;
    }

    public int getLevel(int[] S, int studentScore, int levels) {
        int decrement = 1;
        if (S[0] < studentScore) {
            return levels - decrement;
        }
        for (int i = 1; i < S.length; i++) {
            decrement++;
            if (S[i] < studentScore && studentScore <= S[i - 1]) {
                return levels - decrement;
            }
        }

        return 0;
    }
}