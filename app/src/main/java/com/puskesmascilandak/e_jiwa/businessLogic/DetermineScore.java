package com.puskesmascilandak.e_jiwa.businessLogic;

import android.content.Context;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;

import java.util.ArrayList;
import java.util.List;

public class DetermineScore {
    private Context context;
    private int color;
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

            if (detailCheckUp.getAnswer().equals("Ya")) {
                return true;
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

        if (totalAnsweredYes >= 5 && totalAnsweredYes <= 7) {
            return context.getResources().getColor(R.color.yellow);
        }

        return context.getResources().getColor(R.color.green);
    }

    private int countTotalYesAnswer(List<DetailCheckUp> detailCheckUps) {
        int total = 0;

        for (DetailCheckUp detailCheckUp: detailCheckUps) {
            if (detailCheckUp.getAnswer().equals("Ya")) {
                total += 1;
            }
        }

        return total;
    }
}
