package com.fy.service.statistics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.dto.GameQuery;
import com.fy.service.statistics.model.game.GameFruitModel;
import com.fy.service.statistics.model.game.GameJinModel;
import com.fy.service.statistics.model.game.GameLeiModel;
import com.fy.service.statistics.model.game.GameLuckyModel;
import com.fy.service.statistics.model.game.GameNiuModel;
import com.fy.service.statistics.model.gameOverview.ColumnSumModel;
import com.fy.service.statistics.model.gameOverview.FrequencyGrabPackageModel;
import com.fy.service.statistics.model.gameOverview.FrequencyHairBagModel;
import com.fy.service.statistics.model.gameOverview.GameProfitModel;
import com.fy.service.statistics.model.gameOverview.PeopleGameModel;
import com.fy.service.statistics.model.gameOverview.PeopleGrabPackageModel;
import com.fy.service.statistics.model.gameOverview.PeopleHairBagModel;
import com.fy.service.statistics.model.gameOverview.ProfitAmountGrabModel;
import com.fy.service.statistics.model.gameOverview.ProfitAmountModel;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏概况统计
 */
@Service
@Slf4j
public class GameStatisticService {

    @Autowired
    EsQueryUtils esQueryUtils;


    /**
     * 游戏统计
     *
     * @param param
     * @return
     */
    public Map<String, List> GameOverview(GameQuery param) {
        SearchResponse Search = null;
        SearchResponse Search1 = null;

        if (param.getGame().equals("1")) {
            String[] fields = new String[]{"lei_get_payout_money", "niu_get_payout_money", "create_date", "sumall_40", "niu_payout_money", "niu_send_count_user", "niu_grab_count_user", "niu_game_count_user", "niu_send_count", "niu_grab_count", "lei_send_money", "lei_grab_money", "lei_payout_money", "lei_send_count_user", "lei_grab_count_user", "lei_game_count_user", "lei_send_count", "lei_grab_count", "jin_send_money", "jin_grab_money", "jin_payout_money_out", "jin_send_count_user", "jin_grab_count_user", "jin_game_count_user", "jin_send_count", "jin_grab_count", "sumall_36", "sumall_37", "count_36", "count_id_36", "count_id_37", "sumall_32", "count_32", "count_id_32"};
            Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), param.getUserFlag(), param.getBeginTime(), param.getEndTime(), null, fields);
        } else if (param.getGame().equals("2")) {
            String[] fields = new String[]{"create_date", "jin_payout_money_out", "niu_payout_money", "lei_payout_money", "lei_send_money", "lei_grab_money", "lei_send_count_user", "lei_grab_count_user", "lei_send_count", "lei_grab_count", "lei_game_count_user", "niu_send_money", "niu_grab_money", "niu_send_count_user", "niu_grab_count_user", "niu_send_count", "niu_grab_count", "niu_game_count_user", "jin_send_money", "jin_grab_money", "jin_send_count_user", "jin_grab_count_user", "jin_send_count", "jin_grab_count", "jin_game_count_user", "count_36", "count_32"};
            Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), param.getUserFlag(), param.getBeginTime(), param.getEndTime(), null, fields);
        } else if (param.getGame().equals("3")) {
            String[] fields = new String[]{"create_date", "sumall_40", "niu_payout_money_in", "niu_payout_money_out", "lei_payout_money_death_in", "lei_payout_money_other_in", "lei_grab_money_death_in", "lei_grab_money_other_in", "lei_payout_money_death_out", "lei_payout_money_other_out", "lei_grab_money_out", "jin_grab_money_in", "jin_payout_money_out", "sumall_36", "sumall_37", "sumall_32", "platform_money_in", "platform_money_out"};
            Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), param.getUserFlag(), param.getBeginTime(), param.getEndTime(), null, fields);

            List<AggregationBuilder> aggs = new ArrayList<>();
            TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
            aggregation.subAggregation(AggregationBuilders.sum("sumall_40").field("sumall_40"));
            aggregation.subAggregation(AggregationBuilders.sum("niu_payout_money_in").field("niu_payout_money_in"));
            aggregation.subAggregation(AggregationBuilders.sum("niu_payout_money_out").field("niu_payout_money_out"));
            aggregation.subAggregation(AggregationBuilders.sum("lei_payout_money_death_in").field("lei_payout_money_death_in"));
            aggregation.subAggregation(AggregationBuilders.sum("lei_payout_money_other_in").field("lei_payout_money_other_in"));
            aggregation.subAggregation(AggregationBuilders.sum("lei_grab_money_death_in").field("lei_grab_money_death_in"));
            aggregation.subAggregation(AggregationBuilders.sum("lei_grab_money_other_in").field("lei_grab_money_other_in"));
            aggregation.subAggregation(AggregationBuilders.sum("lei_payout_money_death_out").field("lei_payout_money_death_out"));
            aggregation.subAggregation(AggregationBuilders.sum("lei_payout_money_other_out").field("lei_payout_money_other_out"));
            aggregation.subAggregation(AggregationBuilders.sum("lei_grab_money_out").field("lei_grab_money_out"));
            aggregation.subAggregation(AggregationBuilders.sum("jin_grab_money_in").field("jin_grab_money_in"));
            aggregation.subAggregation(AggregationBuilders.sum("jin_payout_money_out").field("jin_payout_money_out"));
            aggregation.subAggregation(AggregationBuilders.sum("sumall_36").field("sumall_36"));
            aggregation.subAggregation(AggregationBuilders.sum("sumall_37").field("sumall_37"));
            aggs.add(aggregation);
            String[] fields1 = new String[]{"owner_code", "sumall_40", "niu_payout_money_in", "niu_payout_money_out", "lei_payout_money_death_in", "lei_payout_money_other_in", "lei_grab_money_death_in", "lei_grab_money_other_in", "lei_payout_money_death_out", "lei_payout_money_other_out", "lei_grab_money_out", "jin_grab_money_in", "jin_payout_money_out", "sumall_36", "sumall_37"};
            Search1 = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), param.getUserFlag(), param.getBeginTime(), param.getEndTime(), aggs, fields1);

        }
        Map<String, List> map = new HashMap();
        List<GameNiuModel> GameNiuModel = new ArrayList();
        List<GameLeiModel> GameLeiModel = new ArrayList();
        List<GameJinModel> GameJinModel = new ArrayList();
        List<GameFruitModel> GameFruitModel = new ArrayList();
        List<GameLuckyModel> GameLuckyModel = new ArrayList();
        List<ProfitAmountModel> ProfitAmountModel = new ArrayList();
        List<ProfitAmountGrabModel> ProfitAmountGrabModel = new ArrayList();
        List<PeopleHairBagModel> PeopleHairBagModel = new ArrayList();
        List<PeopleGrabPackageModel> PeopleGrabPackageModel = new ArrayList();
        List<PeopleGameModel> PeopleGameModel = new ArrayList();
        List<FrequencyHairBagModel> FrequencyHairBagModel = new ArrayList();
        List<FrequencyGrabPackageModel> FrequencyGrabPackageModel = new ArrayList();
        List<GameProfitModel> GameProfitModel = new ArrayList();
        List<ColumnSumModel> ColumnSumModel = new ArrayList();

        SearchHits hits = Search.getHits();
        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {

            if (param.getGame().equals("1")) {
                for (SearchHit hit : hits) {
                    //牛牛游戏
                    GameNiuModel gameNiuModel = new GameNiuModel();
                    gameNiuModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    gameNiuModel.setNiu_formalities_amount(MathUtils.toDecimalUp(Math.abs((Double) hit.getSourceAsMap().get("sumall_40"))));//牛牛手续费金额
                    gameNiuModel.setNiu_Pay_amount(MathUtils.toDecimalUp(
                            (Double) hit.getSourceAsMap().get("niu_payout_money")
                    ));//牛牛赔付金额
                    gameNiuModel.setNiu_get_payout_money(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("niu_get_payout_money")));//牛牛获赔金额
                    gameNiuModel.setNiu_people_number((Integer) hit.getSourceAsMap().get("niu_send_count_user"));//牛牛发包人数
                    gameNiuModel.setNiu_grabbing_people_number((Integer) hit.getSourceAsMap().get("niu_grab_count_user"));//牛牛抢包人数
                    gameNiuModel.setNiu_game_people_number((Integer) hit.getSourceAsMap().get("niu_game_count_user"));//牛牛游戏人数
                    gameNiuModel.setNiu_frequency_number((Integer) hit.getSourceAsMap().get("niu_send_count"));//牛牛发包频次
                    gameNiuModel.setNiu_grabbing_frequency_number((Integer) hit.getSourceAsMap().get("niu_grab_count"));//牛牛抢包频次
                    GameNiuModel.add(gameNiuModel);
                    //扫雷游戏
                    GameLeiModel gameLeiModel = new GameLeiModel();
                    gameLeiModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    gameLeiModel.setLei_hair_bag_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_send_money")));//扫雷发包金额
                    gameLeiModel.setLei_grab_package_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money")));//扫雷抢包金额
                    gameLeiModel.setLei_Pay_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money")));//扫雷赔付金额
                    gameLeiModel.setLei_get_payout_money(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_get_payout_money")));//扫雷获赔金额
                    gameLeiModel.setLei_people_number((Integer) hit.getSourceAsMap().get("lei_send_count_user"));//扫雷发包人数       ===会员没看到
                    gameLeiModel.setLei_grabbing_people_number((Integer) hit.getSourceAsMap().get("lei_grab_count_user"));//扫雷抢包人数
                    gameLeiModel.setLei_game_people_number((Integer) hit.getSourceAsMap().get("lei_game_count_user"));//扫雷游戏人数
                    gameLeiModel.setLei_frequency_number((Integer) hit.getSourceAsMap().get("lei_send_count"));//扫雷发包频次
                    gameLeiModel.setLei_grabbing_frequency_number((Integer) hit.getSourceAsMap().get("lei_grab_count"));//扫雷抢包频次
                    GameLeiModel.add(gameLeiModel);
                    //禁抢游戏
                    GameJinModel gameJinModel = new GameJinModel();
                    //会员-内部号
                    if (param.getUserFlag().equals(2)) {
                        gameJinModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                        gameJinModel.setJin_hair_bag_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_send_money")));//禁抢发包金额
                        gameJinModel.setJin_people_number((Integer) hit.getSourceAsMap().get("jin_send_count_user"));//禁抢发包人数
                        gameJinModel.setJin_game_people_number((Integer) hit.getSourceAsMap().get("jin_game_count_user"));//禁抢游戏人数
                        gameJinModel.setJin_frequency_number((Integer) hit.getSourceAsMap().get("jin_send_count"));//禁抢发包频次
                        gameJinModel.setJin_get_Pay_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_payout_money_out")));//禁抢获赔金额
                    } else if (param.getUserFlag().equals(3)) { //内部号-会员
                        gameJinModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                        gameJinModel.setJin_hair_bag_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_send_money")));//禁抢发包金额
                        gameJinModel.setJin_grab_package_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_grab_money")));//禁抢抢包金额
                        gameJinModel.setJin_Pay_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_payout_money_out")));//禁抢赔付金额
                        gameJinModel.setJin_people_number((Integer) hit.getSourceAsMap().get("jin_send_count_user"));//禁抢发包人数
                        gameJinModel.setJin_grabbing_people_number((Integer) hit.getSourceAsMap().get("jin_grab_count_user"));//禁抢抢包人数
                        gameJinModel.setJin_game_people_number((Integer) hit.getSourceAsMap().get("jin_game_count_user"));//禁抢游戏人数
                        gameJinModel.setJin_frequency_number((Integer) hit.getSourceAsMap().get("jin_send_count"));//禁抢发包频次
                        gameJinModel.setJin_grabbing_frequency_number((Integer) hit.getSourceAsMap().get("jin_grab_count"));//禁抢抢包频次
                    }
                    GameJinModel.add(gameJinModel);
                    //水果机游戏
                    GameFruitModel gameFruitModel = new GameFruitModel();
                    gameFruitModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    gameFruitModel.setFruit_Betting_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_36")));//水果机投注金额
                    gameFruitModel.setFruit_Winning_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_37")));//水果机中奖金额
                    gameFruitModel.setFruit_game_people_number((Integer) hit.getSourceAsMap().get("count_36"));//水果机游戏人数
                    gameFruitModel.setFruit_Betting_number((Integer) hit.getSourceAsMap().get("count_id_36"));//水果机投注次数
                    gameFruitModel.setFruit_Winning_number((Integer) hit.getSourceAsMap().get("count_id_37"));//水果机中奖次数
                    GameFruitModel.add(gameFruitModel);
                    //幸运大转盘游戏
                    GameLuckyModel gameLuckyModel = new GameLuckyModel();
                    gameLuckyModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());
                    gameLuckyModel.setLucky_game_people_number((Integer) hit.getSourceAsMap().get("count_32"));//幸运大转盘游戏人数
                    gameLuckyModel.setLucky_Winning_number((Integer) hit.getSourceAsMap().get("count_id_32"));//幸运大转盘中奖次数
                    GameLuckyModel.add(gameLuckyModel);

                }
                map.put("GameNiuModel", GameNiuModel);
                map.put("GameLeiModel", GameLeiModel);
                map.put("GameJinModel", GameJinModel);
                map.put("GameFruitModel", GameFruitModel);
                map.put("GameLuckyModel", GameLuckyModel);
            } else if (param.getGame().equals("2")) {

                for (SearchHit hit : hits) {
                    //发包金额
                    ProfitAmountModel profitAmountModel = new ProfitAmountModel();
                    profitAmountModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    profitAmountModel.setNiu_game_amount(MathUtils.toDecimalUp(
                            (Double) hit.getSourceAsMap().get("niu_send_money")
                    )); //牛牛发包金额
                    profitAmountModel.setLei_game_amount(MathUtils.toDecimalUp(
                            (Double) hit.getSourceAsMap().get("lei_send_money")
                    )); //扫雷发包金额
                    profitAmountModel.setJin_game_amount(MathUtils.toDecimalUp(
                            (Double) hit.getSourceAsMap().get("jin_send_money")
                    ));//禁枪发包金额
                    ProfitAmountModel.add(profitAmountModel);

                    //抢包金额
                    ProfitAmountGrabModel profitAmountGrabModel = new ProfitAmountGrabModel();
                    //会员-内部号
                    if (param.getUserFlag().equals(2)) {
                        profitAmountGrabModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                        profitAmountGrabModel.setNiu_game_grab_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("niu_grab_money")));//牛牛抢包金额
                        profitAmountGrabModel.setLei_game_grab_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money")));//扫雷抢包金额
                    } else if (param.getUserFlag().equals(3)) { //内部号-会员
                        profitAmountGrabModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                        profitAmountGrabModel.setNiu_game_grab_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("niu_grab_money")));//牛牛抢包金额
                        profitAmountGrabModel.setLei_game_grab_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money")));//扫雷抢包金额
                        profitAmountGrabModel.setJin_game_grab_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_grab_money")));//禁抢抢包金额
                    }
                    ProfitAmountGrabModel.add(profitAmountGrabModel);
                    //发包人数
                    PeopleHairBagModel peopleHairBag = new PeopleHairBagModel();
                    peopleHairBag.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    peopleHairBag.setJin_people_number((Integer) hit.getSourceAsMap().get("jin_send_count_user"));//禁抢发包人数
                    peopleHairBag.setLei_people_number((Integer) hit.getSourceAsMap().get("lei_send_count_user"));//扫雷发包人数
                    peopleHairBag.setNiu_people_number((Integer) hit.getSourceAsMap().get("niu_send_count_user"));//牛牛发包人数
                    PeopleHairBagModel.add(peopleHairBag);
                    //抢包人数
                    PeopleGrabPackageModel peopleGrabPackage = new PeopleGrabPackageModel();
                    //会员-内部号
                    if (param.getUserFlag().equals(2)) {
                        peopleGrabPackage.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                        peopleGrabPackage.setNiu_grabbing_people_number((Integer) hit.getSourceAsMap().get("niu_grab_count_user"));//牛牛抢包人数
                        peopleGrabPackage.setLei_grabbing_people_number((Integer) hit.getSourceAsMap().get("lei_grab_count_user"));//扫雷抢包人数
                    } else if (param.getUserFlag().equals(3)) { //内部号-会员
                        peopleGrabPackage.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                        peopleGrabPackage.setNiu_grabbing_people_number((Integer) hit.getSourceAsMap().get("niu_grab_count_user"));//牛牛抢包人数
                        peopleGrabPackage.setLei_grabbing_people_number((Integer) hit.getSourceAsMap().get("lei_grab_count_user"));//扫雷抢包人数
                        peopleGrabPackage.setJin_grabbing_people_number((Integer) hit.getSourceAsMap().get("jin_grab_count_user"));//禁抢抢包人数
                    }
                    PeopleGrabPackageModel.add(peopleGrabPackage);
                    //游戏人数
                    PeopleGameModel peopleGame = new PeopleGameModel();
                    peopleGame.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    peopleGame.setNiu_game_people_number((Integer) hit.getSourceAsMap().get("niu_game_count_user"));//牛牛游戏总人数
                    peopleGame.setLei_game_people_number((Integer) hit.getSourceAsMap().get("lei_game_count_user"));//扫雷游戏人数
                    peopleGame.setJin_game_people_number((Integer) hit.getSourceAsMap().get("jin_game_count_user"));//禁抢游戏人数
                    peopleGame.setFruit_game_people_number((Integer) hit.getSourceAsMap().get("count_36"));//水果机游戏人数
                    peopleGame.setLucky_game_people_number((Integer) hit.getSourceAsMap().get("count_32"));//幸运大转盘游戏人数
                    PeopleGameModel.add(peopleGame);

                    //发包频次
                    FrequencyHairBagModel frequencyHairBag = new FrequencyHairBagModel();
                    frequencyHairBag.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    frequencyHairBag.setNiu_frequency_number((Integer) hit.getSourceAsMap().get("niu_send_count"));//牛牛发包频次
                    frequencyHairBag.setLei_frequency_number((Integer) hit.getSourceAsMap().get("lei_send_count"));//扫雷发包频次
                    frequencyHairBag.setJin_frequency_number((Integer) hit.getSourceAsMap().get("jin_send_count"));//禁抢发包频次
                    FrequencyHairBagModel.add(frequencyHairBag);

                    //抢包频次
                    FrequencyGrabPackageModel frequencyGrabPackage = new FrequencyGrabPackageModel();
                    if (param.getUserFlag().equals(2)) {
                        frequencyGrabPackage.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                        frequencyGrabPackage.setNiu_grabbing_frequency_number((Integer) hit.getSourceAsMap().get("niu_grab_count"));//牛牛抢包频次
                        frequencyGrabPackage.setLei_grabbing_frequency_number((Integer) hit.getSourceAsMap().get("lei_grab_count"));//扫雷抢包频次
                    } else if (param.getUserFlag().equals(3)) { //内部号-会员
                        frequencyGrabPackage.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                        frequencyGrabPackage.setNiu_grabbing_frequency_number((Integer) hit.getSourceAsMap().get("niu_grab_count"));//牛牛抢包频次
                        frequencyGrabPackage.setLei_grabbing_frequency_number((Integer) hit.getSourceAsMap().get("lei_grab_count"));//扫雷抢包频次
                        frequencyGrabPackage.setJin_grabbing_frequency_number((Integer) hit.getSourceAsMap().get("jin_grab_count"));//禁抢抢包频次
                    }
                    FrequencyGrabPackageModel.add(frequencyGrabPackage);
                }
                map.put("ProfitAmountModel", ProfitAmountModel);
                map.put("ProfitAmountGrabModel", ProfitAmountGrabModel);
                map.put("PeopleHairBagModel", PeopleHairBagModel);
                map.put("PeopleGrabPackageModel", PeopleGrabPackageModel);
                map.put("PeopleGameModel", PeopleGameModel);
                map.put("FrequencyHairBagModel", FrequencyHairBagModel);
                map.put("FrequencyGrabPackageModel", FrequencyGrabPackageModel);

            } else if (param.getGame().equals("3")) {
                for (SearchHit hit : hits) {
                    //游戏盈利金额
                    GameProfitModel gameProfitModel = new GameProfitModel();
                    gameProfitModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    gameProfitModel.setNiu_game_money(
                            MathUtils.toDecimalUp(Math.abs((Double) hit.getSourceAsMap().get("sumall_40")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("niu_payout_money_in")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("niu_payout_money_out")))
                    );//牛牛游戏金额
                    gameProfitModel.setLei_game_money(
                            MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money_death_in"))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money_other_in")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money_death_in")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money_other_in")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money_death_out")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money_other_out")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money_out")))
                    );//扫雷游戏金额

                    gameProfitModel.setJin_game_money(
                            MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_grab_money_in"))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_payout_money_out")))
                    );//禁抢游戏金额

                    gameProfitModel.setFruit_game_money(
                            MathUtils.toDecimalUp(Math.abs((Double) hit.getSourceAsMap().get("sumall_36")))
                                    .subtract(MathUtils.toDecimalUp(Math.abs((Double) hit.getSourceAsMap().get("sumall_37"))))
                    );//水果机游戏金额

                    gameProfitModel.setTotal_game_money(
                            MathUtils.toDecimalUp(Math.abs((Double) hit.getSourceAsMap().get("sumall_40")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("niu_payout_money_in")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("niu_payout_money_out")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money_death_in")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money_other_in")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money_death_in")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money_other_in")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money_death_out")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_payout_money_other_out")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("lei_grab_money_out")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_grab_money_in")))
                                    .subtract(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("jin_payout_money_out")))
                                    .add(MathUtils.toDecimalUp(Math.abs((Double) hit.getSourceAsMap().get("sumall_36"))))
                                    .subtract(MathUtils.toDecimalUp(Math.abs((Double) hit.getSourceAsMap().get("sumall_37"))))
                    );//总计金额
                    GameProfitModel.add(gameProfitModel);
                }
                Terms agg = Search1.getAggregations().get("agg");
                if (agg.getBuckets().size() > 0) {
                    ColumnSumModel columnSumModel = new ColumnSumModel();

                    Aggregations list = agg.getBuckets().get(0).getAggregations();
                    double sumall_40 = ((Sum) list.get("sumall_40")).getValue();
                    double niu_payout_money_in = ((Sum) list.get("niu_payout_money_in")).getValue();
                    double niu_payout_money_out = ((Sum) list.get("niu_payout_money_out")).getValue();
                    double lei_payout_money_death_in = ((Sum) list.get("lei_payout_money_death_in")).getValue();
                    double lei_payout_money_other_in = ((Sum) list.get("lei_payout_money_other_in")).getValue();
                    double lei_grab_money_death_in = ((Sum) list.get("lei_grab_money_death_in")).getValue();
                    double lei_grab_money_other_in = ((Sum) list.get("lei_grab_money_other_in")).getValue();
                    double lei_payout_money_death_out = ((Sum) list.get("lei_payout_money_death_out")).getValue();
                    double lei_payout_money_other_out = ((Sum) list.get("lei_payout_money_other_out")).getValue();
                    double lei_grab_money_out = ((Sum) list.get("lei_grab_money_out")).getValue();
                    double jin_grab_money_in = ((Sum) list.get("jin_grab_money_in")).getValue();
                    double jin_payout_money_out = ((Sum) list.get("jin_payout_money_out")).getValue();
                    double sumall_36 = ((Sum) list.get("sumall_36")).getValue();
                    double sumall_37 = ((Sum) list.get("sumall_37")).getValue();
                    columnSumModel.setNiu_game_money(
                            MathUtils.toDecimalUp(sumall_40)
                                    .add(MathUtils.toDecimalUp(niu_payout_money_in))
                                    .subtract(MathUtils.toDecimalUp(niu_payout_money_out))
                    );//牛牛游戏合计金额
                    columnSumModel.setLei_game_money(
                            MathUtils.toDecimalUp(lei_payout_money_death_in)
                                    .add(MathUtils.toDecimalUp(lei_payout_money_other_in))
                                    .add(MathUtils.toDecimalUp(lei_grab_money_death_in))
                                    .add(MathUtils.toDecimalUp(lei_grab_money_other_in))
                                    .subtract(MathUtils.toDecimalUp(lei_payout_money_death_out))
                                    .subtract(MathUtils.toDecimalUp(lei_payout_money_other_out))
                                    .subtract(MathUtils.toDecimalUp(lei_grab_money_out))
                    );//扫雷游戏合计金额

                    columnSumModel.setJin_game_money(
                            MathUtils.toDecimalUp(jin_grab_money_in)
                                    .subtract(MathUtils.toDecimalUp(jin_payout_money_out))
                    );//禁抢游戏合计金额
                    columnSumModel.setFruit_game_money(
                            MathUtils.toDecimalUp(Math.abs(sumall_36))
                                    .subtract(MathUtils.toDecimalUp(Math.abs(sumall_37)))
                    );//水果机游戏合计金额
                    ColumnSumModel.add(columnSumModel);
                }
                map.put("GameProfitModel", GameProfitModel);
                map.put("ColumnSumModel", ColumnSumModel);

            }
        } else {

            if (param.getGame().equals("1")) {
                map.put("GameNiuModel", GameNiuModel);
                map.put("GameLeiModel", GameLeiModel);
                map.put("GameJinModel", GameJinModel);
                map.put("GameFruitModel", GameFruitModel);
                map.put("GameLuckyModel", GameLuckyModel);
            } else if (param.getGame().equals("2")) {
                map.put("ProfitAmountModel", ProfitAmountModel);
                map.put("ProfitAmountGrabModel", ProfitAmountGrabModel);
                map.put("PeopleHairBagModel", PeopleHairBagModel);
                map.put("PeopleGrabPackageModel", PeopleGrabPackageModel);
                map.put("PeopleGameModel", PeopleGameModel);
                map.put("FrequencyHairBagModel", FrequencyHairBagModel);
                map.put("FrequencyGrabPackageModel", FrequencyGrabPackageModel);
            } else if (param.getGame().equals("3")) {
                map.put("GameProfitModel", GameProfitModel);
                map.put("ColumnSumModel", ColumnSumModel);
            }
        }
        return map;
    }


}
