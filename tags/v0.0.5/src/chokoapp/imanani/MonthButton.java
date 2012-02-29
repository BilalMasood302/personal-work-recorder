package chokoapp.imanani;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

public class MonthButton extends Button {

    private Date date;
    private SimpleDateFormat df;

    public MonthButton(Context context) {
        this(context, null);
    }

    public MonthButton(Context context, AttributeSet attr) {
        super(context, attr);
        date = null;
        int dateformatValue = attr.getAttributeResourceValue(null, "app.dateformat", -1);
        if ( dateformatValue <  0 ) {
            df = new SimpleDateFormat();
        } else {
            df = new SimpleDateFormat(getResources().getText(dateformatValue).toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final DatePicker datePicker = new DatePicker(context);
        LinearLayout datePickerLayout = (LinearLayout)datePicker.getChildAt(0);
        datePickerLayout.getChildAt(2).setVisibility(View.GONE);
        final AlertDialog dialog =
            builder.setView(datePicker)
            .setTitle(context.getString(R.string.select_month))
            .setPositiveButton(android.R.string.ok,
                               new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface d, int w) {
                                       setDate(datePicker);
                                       setButtonText();
                                   }
                               })
            .setNegativeButton(android.R.string.cancel, null)
            .create();

        super.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( dateSelected() ) {
                        datePicker.updateDate(getYear(), getMonth(), getDay());
                    }
                    dialog.show();
                }
            });
    }

    public boolean dateSelected() {
        return date != null;
    }

    public void setButtonText() {
        if ( date != null ) {
            setText(df.format(date));
        } else {
            setText(getContext().getString(R.string.select_month));
        }
    }

    public void setDate(DatePicker datePicker) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, datePicker.getYear());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        date = cal.getTime();
    }

    public void setDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        date = cal.getTime();
    }

    public int getYear() {
        if ( dateSelected() ) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.YEAR);
        } else {
            return 9999;
        }
    }

    public int getMonth() {
        if ( dateSelected() ) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH);
        } else {
            return 12;
        }
    }

    public int getDay() {
        if ( dateSelected() ) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_MONTH);
        } else {
            return 31;
        }
    }
}