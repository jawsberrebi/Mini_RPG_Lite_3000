module com.example.mini_rpg_lite_3000_withinterface {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mini_rpg_lite_3000_withinterface to javafx.fxml;
    exports com.example.mini_rpg_lite_3000_withinterface;
    exports com.example.mini_rpg_lite_3000_withinterface.utils;
    opens com.example.mini_rpg_lite_3000_withinterface.utils to javafx.fxml;
}