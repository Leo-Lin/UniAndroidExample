package me.leolin.sample.listviewwithviewpager.app;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Leolin
 */
public class ComboVo {
    private String id;
    private List<ComboDetailVo> details = new LinkedList<ComboDetailVo>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ComboDetailVo> getDetails() {
        return details;
    }

    public void setDetails(List<ComboDetailVo> details) {
        this.details = details;
    }
}
