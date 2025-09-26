package oy.interact.tira.task_06.model;

import java.util.List;

public interface ShopListener {
    void shopQueueStateChanged(LittleChocolateShop.RefreshReason reason, final List<String> queueNames);
    void shopOpened();
    void shopIsClosing();
}
