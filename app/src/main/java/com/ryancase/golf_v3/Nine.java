package com.ryancase.golf_v3;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class Nine {
    private List<Hole> holes;

    public Nine() {
        holes = new ArrayList<>();
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public void setHoles(List<Hole> holes) {
        this.holes = holes;
    }

    public void addHole(Hole hole) {
        if (holes != null) {
            holes.add(hole);
        }
    }

    public int getPar() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getPar();
        }

        return retval;
    }

    public int getScore() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getScore();
        }

        return retval;
    }

    public int getScoreToPar() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getScoreToPar();
        }

        return retval;
    }

    public int getPutts() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getPutts();
        }

        return retval;
    }

    public int getGreens() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getGreen();
        }

        return retval;
    }

    public int getFairways() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getFairway();
        }

        return retval;
    }

    public String getFairwayPercentage() {
        String retval;
        float percent;
        float numOfPossibleFairways = 0;

        for (Hole hole : holes) {
            if (hole.getPar() != 3) {
                numOfPossibleFairways += 1;
            }
        }
        float fairwaysHit = getFairways();
        percent = fairwaysHit / numOfPossibleFairways;
        percent *= 100;
        retval = String.format("%.2f", percent);
        retval = retval + "%";
        return retval;
    }

    public String getGreenPercentage() {
        String retval;
        float percent;
        float numOfPossibleGreens = 18;

        float greensHit = getGreens();
        percent = greensHit / numOfPossibleGreens;
        percent *= 100;
        retval = String.format("%.2f", percent);
        retval = retval + "%";
        return retval;
    }

    public String madePuttsPercentage() {
        String retval;
        float percent;
        float puttsMade = 0;

        for (Hole hole : holes) {
            if (hole.getPutts() > 0) {
                puttsMade += 1;
            }
        }

        float numOfPutts = getPutts();
        percent = puttsMade / numOfPutts;
        if(Float.isNaN(percent)) {
            return 0 + "%";
        }
        percent *= 100;
        retval = String.format("%.2f", percent);
        retval = retval + "%";

        return retval;
    }

    public String getAverageDriverRating() {
        String retval;
        float total = 0;
        float drivingHoles = 0;

        for(Hole hole : holes) {
            if(hole.getDriverRating() != 0) {
                total += hole.getDriverRating();
                drivingHoles++;
            }
        }

        float average = total / drivingHoles;


        retval = getLetterGrade(average);

        //        if(average <= 1) {
//            retval = "A";
//        } else if(average > 1 && average <= 1.30) {
//            retval = "A-";
//        } else if(average > 1.30 && average <= 1.75) {
//            retval = "B+";
//        } else if(average > 1.75 && average <= 2) {
//            retval = "B";
//        } else if(average > 2  && average <= 2.30) {
//            retval = "B-";
//        } else if(average > 2.30 && average <= 2.75) {
//            retval = "C+";
//        } else if(average > 2.75 && average <= 3) {
//            retval = "C";
//        } else if(average > 3 && average <= 3.30) {
//            retval = "C-";
//        } else {
//            retval = "D";
//        }

        return retval;
    }

    public float getAverageDriverRatingAsFloat() {
        float total = 0;
        float drivingHoles = 0;

        for(Hole hole : holes) {
            if(hole.getDriverRating() != 0) {
                total += hole.getDriverRating();
                drivingHoles++;
            }
        }

        float average = total / drivingHoles;

        return average;
    }

    public String getAverageIronRating() {
        String retval;
        float total = 0;
        float validHoles = 0;

        for(Hole hole : holes) {
            if(hole.getIronRating() != 0) {
                total += hole.getIronRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        retval = getLetterGrade(average);

        return retval;
    }

    public float getAverageIronRatingAsFloat() {
        float total = 0;
        float validHoles = 0;

        for(Hole hole : holes) {
            if(hole.getIronRating() != 0) {
                total += hole.getIronRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        return average;
    }

    public String getAverageApproachRating() {
        String retval;
        float total = 0;
        float validHoles = 0;

        for(Hole hole : holes) {
            if(hole.getPuttRating() != 0) {
                total += hole.getPuttRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        retval = getLetterGrade(average);

        return retval;
    }

    public float getAverageApproachRatingAsFloat() {
        float total = 0;
        float validHoles = 0;

        for(Hole hole : holes) {
            if(hole.getApproachRating() != 0) {
                total += hole.getApproachRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        return average;
    }

    public String getAveragePuttRating() {
        String retval;
        float total = 0;
        float validHoles = 0;

        for(Hole hole : holes) {
            if(hole.getPuttRating() != 0) {
                total += hole.getPuttRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        retval = getLetterGrade(average);

        return retval;
    }

    public float getAveragePuttRatingAsFloat() {
        float total = 0;
        float validHoles = 0;

        for(Hole hole : holes) {
            if(hole.getPuttRating() != 0) {
                total += hole.getPuttRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        return average;
    }

    private String getLetterGrade(float average) {
        String retval;

        if(average <= 1) {
            retval = "A";
        } else if(average > 1 && average <= 1.30) {
            retval = "A-";
        } else if(average > 1.30 && average <= 1.75) {
            retval = "B+";
        } else if(average > 1.75 && average <= 2) {
            retval = "B";
        } else if(average > 2  && average <= 2.30) {
            retval = "B-";
        } else if(average > 2.30 && average <= 2.75) {
            retval = "C+";
        } else if(average > 2.75 && average <= 3) {
            retval = "C";
        } else if(average > 3 && average <= 3.30) {
            retval = "C-";
        } else {
            retval = "D";
        }

        return retval;
    }

}
