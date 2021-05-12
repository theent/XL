package model;

import java.util.Map;

public interface OnUpdateObserver {
    void onUpdate(Map.Entry<String, String> entry);
}
