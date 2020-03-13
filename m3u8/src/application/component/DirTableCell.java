package application.component;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import application.dto.TableItem;
import application.utils.CommonUtility;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DirTableCell extends TableCell<TableItem, String> {

	public DirTableCell() {
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setTooltip(new Tooltip("双击打开文件夹"));

			}
		});
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
					TableItem item = (TableItem) getTableRow().getItem();
					if (null != item) {
						try {
							File file = new File(item.getDir());
							if (!file.isDirectory()) {
								CommonUtility.alert("文件夹错误");
							} else {
								Desktop.getDesktop().open(file);
							}
						} catch (IOException e) {

						}
					}
				}

			}
		});
	}

	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		this.setText(item);
	}

}
