package nge.lk.mods.commonlib.gui.designer;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import nge.lk.mods.commonlib.gui.designer.element.Box;
import nge.lk.mods.commonlib.gui.designer.element.Button;
import nge.lk.mods.commonlib.gui.designer.element.Label;
import nge.lk.mods.commonlib.gui.designer.element.RootBox;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a custom designed GUI.
 *
 * @see net.minecraft.client.gui.GuiScreen
 */
public abstract class GuiDesigner extends GuiScreen {

    /**
     * The root element.
     */
    protected final Box root = new RootBox();

    /**
     * The buttons in this GUI.
     */
    private final List<Button> buttons = new ArrayList<>();

    /**
     * The labels in this GUI.
     */
    private final List<Label> labels = new ArrayList<>();

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        drawDefaultBackground();

        // Draw the labels in this GUI.
        for (final Label label : labels) {
            drawString(fontRenderer, label.getCaption(), label.getPositionX(), label.getPositionY(), label.getColor());
        }

        // Draw the input elements.
        /*for (final InputElement element : inputElements) {
            element.getTextField().drawTextBox(); TODO
        }*/

        // Takes care of buttons etc.
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        // Clear GuiScreen's button list.
        buttonList.clear();

        final RenderContext ctx = new RenderContext(b -> {
            buttonList.add(b.getButton());
            buttons.add(b);
        });
        root.setWidth(width);
        root.setHeight(height);
        root.prepareRender(ctx);

        // Enable repeat events for input elements.
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void updateScreen() {
        // Update all input elements.
        /*for (final InputElement input : inputElements) {
            input.getTextField().updateCursorCounter(); TODO
        }*/
    }

    @Override
    public void onGuiClosed() {
        // Disable repeat events (for ingame keyboard mechanics).
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        // All GUIs use ESC for closing.
        if (keyCode == Keyboard.KEY_ESCAPE) {
            closeGui();
        }

        // Forward key events to all input elements.
        /*for (final InputElement element : inputElements) {
            element.getTextField().textboxKeyTyped(typedChar, keyCode); TODO
        }*/
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        // Forward mouse clicks to all input elements.
        /*for (final InputElement element : inputElements) { TODO
            element.getTextField().mouseClicked(mouseX, mouseY, mouseButton);
        }*/
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        // Only perform actions on enabled buttons.
        if (button.enabled) {
            if (button.id < buttons.size() && button.id >= 0) {
                final Button btn = buttons.get(button.id);
                btn.getButtonListener().accept(btn);
            }
        }
    }

    /**
     * This method should setup all elements for this GUI.
     * <p>
     * The derived class has to call it by itself before the GUI is opened.
     */
    protected abstract void createGui();

    /**
     * Closes this GUI.
     */
    protected void closeGui() {
        mc.displayGuiScreen(null);
    }
}
