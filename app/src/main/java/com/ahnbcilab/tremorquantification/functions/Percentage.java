package com.ahnbcilab.tremorquantification.functions;

public class Percentage {
    LineTaskAnalyze lineTaskAnalyze =new LineTaskAnalyze();
    public double[] percentage(double[] measure ,double[] pre_measure ){
        double [] return_value=new double[measure.length];

        for(int i=0;i<measure.length;i++){
            //오차율(%) = (예측치-실측치)/실측치 *100
            //지난 결과 대비 오늘 결과가 몇퍼센트 달라졌는지 알고싶음 -> (오늘 결과 / 지난 결과) = 지난번보다 오늘 이 몇퍼센트 일치한가?
            // -> {(오늘 결과 / 지난 결과)-1} = 지난번 보다 오늘이 몇퍼센트 다른가?
            //음수는 몇 퍼센트 감소 , 양수는 몇퍼센트 증가
            return_value[i] = ((measure[i])/pre_measure[i]-1)*100;
        }
        //TF는 증가하는게 환자들에게 부정적인 것
        //TM도 증가하는게 환자들에게 부정적인 것
        //Time은 증가하든 감소하든 매우 많은 요인들이 있어서 좋다,안 좋다를구별하기 힘듦
        //ED는 증가하는게 환자들에게 부정적인 것
        //Velocity는 증가하든 감소하든 좋다,안 좋다를구별하기 힘듦

        //그러므로 , TF , TM , ED는 증가하는게 부정적인 거라면, 직관적으로 마이너스가 붙어야 더 와닿지않을까?
        //그렇다면 10%가 증가했다 = 10%만큼 트레머가 더 늘어났다 = 10% 악화되었다. -> 제일 밑에 질문이 정답이면 이 문제는 고민할 필요가 없다. ********************************2
        //왜냐하면 모두 똑같이 증가하거나 감소하건나 일테니까 더하면 부호는 같다.

        //그렇다면 TF가 8%증가 , TM이 3%증가 , ED가 5%증가 했으면, 이 값을 모두 더해서 16%가 증가했다고 말을해도 되는것인가???
        //아니면 평균 증감율을 구해서 5.3% 증가했다 라고 말해야 하는가? ********************************3


        //다른 문제로, TF는 10%만큼 감소했는데( = 떨림 주파수가 10% 줄어들었다) , TM은 10%증가했다 - > 이럴수가 있나?
        //Tremor가 증가하면 무조건 TF, TM , ED 도 모두 증가하는가? ********************************1

        //[0]: TM , [1]: TF , [2]:time , [3]: ED , [4]:velocity
        return_value[1]=Math.abs(return_value[1]);
        return  return_value;
    }
}
