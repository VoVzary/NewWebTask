package sample.objects;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Reflection;
import javafx.util.Duration;

public class AnimationsAndEffects {

    private ColorAdjust greyEffect = new ColorAdjust();

    private Reflection reflection = new Reflection();

    private ScaleTransition growAndShrink = new ScaleTransition();

    public void reflection (Node node){
        node.setEffect(reflection);
    }

    public void growAndShrink(Node node, double d){

        growAndShrink.setDuration(Duration.millis(300));
        growAndShrink.setNode(node);
        growAndShrink.setToX(d);
        growAndShrink.setToY(d);
        growAndShrink.playFromStart();

    }

    public void greyEffectBritness (double d){
        greyEffect.setBrightness(d);
        reflection.setInput(greyEffect);
    }

    public void greyEffectContrast (Node node, double d){
        greyEffect.setContrast(d);
        node.setEffect(greyEffect);

    }

    public ColorAdjust getGreyEffect() {
        return greyEffect;
    }

    public void setGreyEffect(ColorAdjust greyEffect) {
        this.greyEffect = greyEffect;
    }

    public Reflection getReflection() {
        return reflection;
    }

    public void setReflection(Reflection reflection) {
        this.reflection = reflection;
    }
}
