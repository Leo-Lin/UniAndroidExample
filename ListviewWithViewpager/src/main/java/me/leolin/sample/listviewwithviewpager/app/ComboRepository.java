package me.leolin.sample.listviewwithviewpager.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Leolin
 */
public class ComboRepository {

    private static final Map<String, ComboVo> combos = new HashMap<String, ComboVo>();

    public static void refreshData(JSONArray jsonArray) {
        combos.clear();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ComboVo comboVo = new ComboVo();
                String comboId = jsonObject.getString("Id");
                comboVo.setId(comboId);

                JSONArray detailsArray = jsonObject.getJSONArray("Detail");

                for (int j = 0; j < detailsArray.length(); j++) {
                    JSONObject detailJsonObject = detailsArray.getJSONObject(j);

                    ComboDetailVo detailVo = new ComboDetailVo();

                    detailVo.setProductId(detailJsonObject.getString("Id"));
                    detailVo.setQuantity(detailJsonObject.getInt("Quantity"));

                    comboVo.getDetails().add(detailVo);
                }

                combos.put(comboId, comboVo);
            }


        } catch (JSONException e) {
        }
    }


    public static Map<String, ComboVo> getCombosMap() {
        return combos;
    }

    public static List<ComboVo> getCombos() {
        List<ComboVo> list = new LinkedList<ComboVo>();
        list.addAll(combos.values());
        return list;
    }
}
