package org.gwtbootstrap3.client.ui.base.mixin;

/*
 * #%L
 * GwtBootstrap3
 * %%
 * Copyright (C) 2013 GwtBootstrap3
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.base.HasIcon;
import org.gwtbootstrap3.client.ui.base.HasIconPosition;
import org.gwtbootstrap3.client.ui.constants.IconFlip;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconRotate;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.HasText;

/**
 * Mixin for Widgets that have text and an optional icon.
 *
 * @author Sven Jacobs
 */
public class IconTextMixin<T extends ComplexWidget & HasText & HasIcon & HasIconPosition> implements
        HasText, HasIcon, HasIconPosition {

    private final T widget;
    private final Text text = new Text();
    private final Text separator = new Text(" ");
    private Icon icon;
    private IconType iconType;
    private IconPosition iconPosition = IconPosition.LEFT;
    private IconSize iconSize = IconSize.NONE;
    private IconFlip iconFlip = IconFlip.NONE;
    private IconRotate iconRotate = IconRotate.NONE;
    private boolean iconMuted = false;
    private boolean iconSpin = false;
    private boolean iconBordered = false;
    private boolean iconLight = false;
    private boolean iconFixedWidth = false;

    public IconTextMixin(final T widget) {
        this.widget = widget;
    }

    public void addTextWidgetToParent() {
        widget.add(text);
    }

    @Override
    public void setText(final String text) {
        this.text.setText(text);
    }

    @Override
    public String getText() {
        return text.getText();
    }

    @Override
    public void setIcon(final IconType iconType) {
        this.iconType = iconType;
        render();
    }

    @Override
    public IconType getIcon() {
        return icon == null ? null : icon.getType();
    }

    @Override
    public void setIconPosition(final IconPosition iconPosition) {
        this.iconPosition = iconPosition;
        render();
    }

    @Override
    public IconPosition getIconPosition() {
        return iconPosition;
    }

    @Override
    public void setIconSize(final IconSize iconSize) {
        this.iconSize = iconSize;
        render();
    }

    @Override
    public IconSize getIconSize() {
        return iconSize;
    }

    @Override
    public void setIconFlip(final IconFlip iconFlip) {
        this.iconFlip = iconFlip;
        render();
    }

    @Override
    public IconFlip getIconFlip() {
        return iconFlip;
    }

    @Override
    public void setIconRotate(final IconRotate iconRotate) {
        this.iconRotate = iconRotate;
        render();
    }

    @Override
    public IconRotate getIconRotate() {
        return iconRotate;
    }

    @Override
    public void setIconBordered(final boolean iconBordered) {
        this.iconBordered = iconBordered;
        render();
    }

    @Override
    public boolean isIconBordered() {
        return iconBordered;
    }

    @Override
    public void setIconMuted(final boolean iconMuted) {
        this.iconMuted = iconMuted;
        render();
    }

    @Override
    public boolean isIconMuted() {
        return iconMuted;
    }

    @Override
    public void setIconLight(final boolean iconLight) {
        this.iconLight = iconLight;
        render();
    }

    @Override
    public boolean isIconLight() {
        return iconLight;
    }

    @Override
    public void setIconSpin(final boolean iconSpin) {
        this.iconSpin = iconSpin;
        render();
    }

    @Override
    public boolean isIconSpin() {
        return iconSpin;
    }

    @Override
    public void setIconFixedWidth(final boolean iconFixedWidth) {
        this.iconFixedWidth = iconFixedWidth;
        render();
    }

    @Override
    public boolean isIconFixedWidth() {
        return iconFixedWidth;
    }

    private void render() {
        // We defer to make sure the elements are available to manipulate their positions
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                if (text.isAttached()) {
                    text.removeFromParent();
                }
                if (separator.isAttached()) {
                    separator.removeFromParent();
                }

                if (icon != null) {
                    icon.removeFromParent();
                }

                icon = new Icon();
                icon.setType(iconType);
                icon.setSize(iconSize);
                icon.setFlip(iconFlip);
                icon.setRotate(iconRotate);
                icon.setMuted(iconMuted);
                icon.setSpin(iconSpin);
                icon.setBorder(iconBordered);
                icon.setLight(iconLight);
                icon.setFixedWidth(iconFixedWidth);

                // Since we are dealing with Icon/Text, we can insert them at the right position
                // Helps on widgets like ButtonDropDown, where it has a caret added
                int position = 0;

                if (iconPosition == IconPosition.LEFT) {
                    widget.insert(icon, position++);
                    widget.insert(separator, position++);
                }

                if (text.getText() != null && text.getText().length() > 0) {
                    widget.insert(text, position);
                }

                if (iconPosition == IconPosition.RIGHT) {
                    widget.insert(separator, position++);
                    widget.insert(icon, position);
                }
            }
        });
    }
}
