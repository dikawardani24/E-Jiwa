package com.puskesmascilandak.e_jiwa.business;

import android.content.Context;
import android.text.TextUtils;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;

import java.util.ArrayList;
import java.util.List;

public class DetermineScore {
    private Context context;
    private final List<Integer> forbiddenYesAnswered = new ArrayList<>();

    public DetermineScore(Context context) {
        this.context = context;
        for (int i= 21;i<30; i++) {
            forbiddenYesAnswered.add(i);
        }
    }

    private boolean isForbiddenYesBeingAnswered(List<DetailCheckUp> detailCheckUps) {
        for (int forbid : forbiddenYesAnswered) {
            DetailCheckUp detailCheckUp = detailCheckUps.get(forbid - 1);

            if (detailCheckUp != null) {
                if (detailCheckUp.getAnswer().equals("Ya")) {
                    return true;
                }
            }
        }

        return false;
    }

    public int getColor(List<DetailCheckUp> detailCheckUps) {

        if (isForbiddenYesBeingAnswered(detailCheckUps)) {
            return context.getResources().getColor(R.color.red);
        }

        int totalAnsweredYes = countTotalYesAnswer(detailCheckUps);
        if (totalAnsweredYes >= 8) {
            return context.getResources().getColor(R.color.red);
        }

        if (totalAnsweredYes >= 5) {
            return context.getResources().getColor(R.color.yellow);
        }

        return context.getResources().getColor(R.color.green);
    }

    public int countTotalYesAnswer(List<DetailCheckUp> detailCheckUps) {
        int total = 0;

        for (DetailCheckUp detailCheckUp: detailCheckUps) {
            if (detailCheckUp.getAnswer().equals("Ya")) {
                total += 1;
            }
        }

        return total;
    }

    public String generateKeterangan(List<DetailCheckUp> detailCheckUps) {
        List<String> descriptions = new ArrayList<>();

        int totalYesAnswered = countTotalYesAnswer(detailCheckUps);
        if (totalYesAnswered > 8) descriptions.add("F.32");

        if (isQuestion21BeingYes(detailCheckUps)) descriptions.add("F.10");

        if (isQuestionBetween22Until24BeingYes(detailCheckUps)) descriptions.add("F.20");

        if (isQuestionBetween25Until29BeingYes(detailCheckUps)) descriptions.add("f.43");

        return TextUtils.join(", ", descriptions);
    }

    private boolean isQuestion21BeingYes(List<DetailCheckUp> detailCheckUps) {
        return detailCheckUps.get(20).getAnswer().equals("Ya");
    }

    private boolean isQuestionBetween22Until24BeingYes(List<DetailCheckUp> detailCheckUps) {
        for (int i=21; i<25; i++) {
            if (detailCheckUps.get(i).getAnswer().equals("Ya")) {
                return true;
            }
        }

        return false;
    }

    private boolean isQuestionBetween25Until29BeingYes(List<DetailCheckUp> detailCheckUps) {
        for (int i=24; i<29; i++) {
            if (detailCheckUps.get(i).getAnswer().equals("Ya")) {
                return true;
            }
        }

        return false;
    }
}