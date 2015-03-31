package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author R. Fei
 */
public class InterestRateProvider {
    
    private List<List<Double>> InterestInfo;

        InterestRateProvider(AccountsEnum accountType){
            this.InterestInfo = new ArrayList<>();
            List<Double> ThresholdList = new ArrayList<>();
            List<Double> InterestList = new ArrayList<>();
            switch(accountType){
                case SAVINGS:
                    ThresholdList.add(0,0.0);
                    ThresholdList.add(1,1000.0);
                    InterestList.add(0,0.001);
                    InterestList.add(1,0.002);
                    this.InterestInfo.add(0, ThresholdList);
                    this.InterestInfo.add(1, InterestList);
                    break;
                case MAXI_SAVINGS:
                    ThresholdList.add(0,0.0);
                    ThresholdList.add(1,10.0);
                    InterestList.add(0,0.001);
                    InterestList.add(1,0.05);
                    this.InterestInfo.add(0, ThresholdList);
                    this.InterestInfo.add(1, InterestList);
                    break;
                default:
                    ThresholdList.add(0,0.000);
                    InterestList.add(0,0.001);
                    InterestInfo.add(0, ThresholdList);
                    InterestInfo.add(1, InterestList);
                    break;
            }
        }

        InterestRateProvider(List<List<Double>> InterestInfo){
            this.InterestInfo = new ArrayList<>();
            this.InterestInfo = InterestInfo;
        }

        List<List<Double>> getInterestInfo(){
            return this.InterestInfo;
        }
    }
