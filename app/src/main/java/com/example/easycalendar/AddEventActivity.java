package com.example.easycalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.thebluealliance.spectrum.SpectrumPalette;


import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddEventActivity extends AppCompatActivity implements
        View.OnClickListener,AdapterView.OnItemSelectedListener{
    private String selectedDate;
    private Spinner spinner_notification;
    private Spinner spinner_recurrance;
    private Spinner spinner_category;
    private Spinner spinner_emailNotification;
    private ImageButton btn_setNotificationToNone;
    private ImageButton  btn_setRecurranceToNone;
    private ImageButton  btn_showPalette;
    private Button btn_addEvent;
    private Button btn_back;
    private TextView notiftv;
    private EditText edtTxt_email;
    private EditText edtTxt_notes;
    private EditText edtTxt_eventName;
    private CheckBox chckbox_email;
    private CheckBox checkbox_rememberEmail;

    private TextView tv_startTime;
    private TextView tv_startDate;
    private TextView tv_endTime;
    private TextView tv_endDate;

    TimePickerDialog timePickerDialog;

    private AlertDialog.Builder builder_palette;
    private  View paletteView;
    private  View datePickerView;

    private int eventColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("date");
        Toast.makeText(this, "Tarih : "+selectedDate, Toast.LENGTH_SHORT).show();

        initViews();



        btn_setNotificationToNone.setOnClickListener(this);
        btn_setRecurranceToNone.setOnClickListener(this);
        btn_showPalette.setOnClickListener(this);
        btn_addEvent.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        spinner_notification.setOnItemSelectedListener(this);
        spinner_emailNotification.setOnItemSelectedListener(this);
        spinner_recurrance.setOnItemSelectedListener(this);
        spinner_category.setOnItemSelectedListener(this);
        tv_startTime.setOnClickListener(this);
        tv_startDate.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_endDate.setOnClickListener(this);

        chckbox_email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtTxt_email.setVisibility(View.VISIBLE);
                    edtTxt_email.setClickable(true);
                    checkbox_rememberEmail.setVisibility(View.VISIBLE);
                    if(spinner_emailNotification.getSelectedItemPosition() == 0)
                        spinner_emailNotification.setSelection(spinner_notification.getSelectedItemPosition());
                } else {
                    edtTxt_email.setClickable(false);
                    edtTxt_email.setVisibility(View.INVISIBLE);
                    checkbox_rememberEmail.setVisibility(View.INVISIBLE);
                    spinner_emailNotification.setSelection(0);

                }
            }
        });




    }

    private void showPaletteLayout() {
        LayoutInflater inflater = AddEventActivity.this.getLayoutInflater();
        paletteView = inflater.inflate(R.layout.palette_layout,null);
        builder_palette = new AlertDialog.Builder(AddEventActivity.this);
        builder_palette.setTitle("Renk Paleti");
        builder_palette.setMessage("Etkinlik Türüne Özel Renk Seçimi");
        builder_palette.setView(paletteView);
        builder_palette.setPositiveButton("Tamam", null);
        builder_palette.show();
        SpectrumPalette spectrumPalette = paletteView.findViewById(R.id.palette);
        spectrumPalette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                Toast.makeText(AddEventActivity.this, "CoLOR : " + color, Toast.LENGTH_SHORT).show();
                btn_showPalette.setBackgroundColor(color);
                eventColor = color;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setNotificationToNone:
                spinner_notification.setSelection(0);
                break;
            case R.id.btn_setRecurranceToNone:
                spinner_recurrance.setSelection(0);
                break;
            case R.id.btn_showPalette:
                showPaletteLayout();
                break;
            case R.id.startTime:
                timePick("start");
                break;
            case R.id.endTime:
                timePick("end");
                break;
            case R.id.starDate:
                mydatepick();
                break;
            case R.id.endDate:
                //TODO
                /* take a parameter start or end date for mydatepick func
                   if user selects a date before the start date, unselect the selection
                 */
                mydatepick();
                break;
            case R.id.btn_completeAddingEvent:
                addEvent();
                break;
            case R.id.AddEventActivity_btn_back:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.spinner_notification:
                    if(position != 0 ) {
                        btn_setNotificationToNone.setVisibility(View.VISIBLE);
                        chckbox_email.setClickable(true);
                        spinner_emailNotification.setVisibility(View.VISIBLE);
                       //chckbox_email.setTextColor(R.color.md_black_1000);
                    }
                    else {
                        btn_setNotificationToNone.setVisibility(View.INVISIBLE);
                        chckbox_email.setClickable(false);
                        spinner_emailNotification.setVisibility(View.INVISIBLE);
                       // chckbox_email.setTextColor(R.color.md_grey_500);
                    }
                    break;
                case R.id.spinner_recurrance:
                    if(position != 0 )
                        btn_setRecurranceToNone.setVisibility(View.VISIBLE);
                    else
                        btn_setRecurranceToNone.setVisibility(View.INVISIBLE);
                    break;
                case R.id.spinner_emailNotification:
                    if(position ==0 )
                        chckbox_email.setChecked(false);
                    else
                        chckbox_email.setChecked(true);
                    break;
                default:
                    break;
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initViews(){



        spinner_notification = findViewById(R.id.spinner_notification);
        spinner_recurrance = findViewById(R.id.spinner_recurrance);
        spinner_category = findViewById(R.id.spinner_category);
        spinner_emailNotification= findViewById(R.id.spinner_emailNotification);
        btn_setNotificationToNone = findViewById(R.id.btn_setNotificationToNone);
        btn_setRecurranceToNone = findViewById(R.id.btn_setRecurranceToNone);
        btn_showPalette = findViewById(R.id.btn_showPalette);
        btn_addEvent = findViewById(R.id.btn_completeAddingEvent);
        btn_back = findViewById(R.id.AddEventActivity_btn_back);
        chckbox_email =findViewById(R.id.checkbo_email);
        checkbox_rememberEmail =findViewById(R.id.checkbox_rememberEmail);
        edtTxt_email = findViewById(R.id.edtTxt_email);
        edtTxt_notes = findViewById(R.id.edtTxt_notes);
        edtTxt_eventName = findViewById(R.id.edtTxt_eventName);
        tv_startTime = findViewById(R.id.startTime);
        tv_startDate = findViewById(R.id.starDate);
        tv_endTime = findViewById(R.id.endTime);
        tv_endDate = findViewById(R.id.endDate);

        ArrayAdapter<CharSequence> adptr_notification = ArrayAdapter.createFromResource(this,
                R.array.notificationOptions_array, android.R.layout.simple_spinner_item);
        adptr_notification.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_notification.setAdapter(adptr_notification);

        spinner_emailNotification.setAdapter(adptr_notification);

        ArrayAdapter<CharSequence> adptr_recurrance = ArrayAdapter.createFromResource(this,
                R.array.recurranceOptions_array, android.R.layout.simple_spinner_item);
        adptr_recurrance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_recurrance.setAdapter(adptr_recurrance);

        ArrayAdapter<CharSequence> adptr_category = ArrayAdapter.createFromResource(this,
                R.array.categoryOptions_array, android.R.layout.simple_spinner_item);
        adptr_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(adptr_category);

        /* Starting positions of spinners */
        spinner_notification.setSelection(1);
        spinner_recurrance.setSelection(0);
        spinner_category.setSelection(0);




    }

    public void timePick(String time_type){

        final Calendar takvim = Calendar.getInstance();
        int saat = takvim.get(Calendar.HOUR_OF_DAY);
        int dakika = takvim.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(AddEventActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // hourOfDay ve minute değerleri seçilen saat değerleridir.
                        Time selectedTime = new Time(hourOfDay,minute);
                        if(time_type.equals("start")) {  //StartTimePicker selected
                            tv_startTime.setText(selectedTime.toString());
                            tv_endTime.setText(selectedTime.toString());
                        }else { //EndTimePicker selected
                            Time startTime = new Time(tv_startTime.getText().toString(),':');
                            Boolean oneDayEvent = tv_startDate.getText().toString().equals(tv_endDate.getText().toString());
                            if( oneDayEvent && selectedTime.isBefore(startTime) )
                                Toast.makeText(AddEventActivity.this, "Hatalı bitiş zamanı", Toast.LENGTH_SHORT).show();
                            else
                                tv_endTime.setText(hourOfDay + ":" + minute);
                        }

                    }
                }, saat, dakika, true);

        tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Tamam", tpd);
        tpd.show();
    }

    public void mydatepick(){
        AlertDialog.Builder builder_date;
        LayoutInflater inflater = AddEventActivity.this.getLayoutInflater();
        datePickerView = inflater.inflate(R.layout.datepick_layout,null);

        builder_date = new AlertDialog.Builder(AddEventActivity.this);
        builder_date.setTitle(" Etkinlik Tarihi");
        builder_date.setMessage("Başlangıç ve bitiş tarihi");
        builder_date.setView(datePickerView);
        builder_date.setPositiveButton("Tamam", null);
        builder_date.show();
        MaterialCalendarView calendarView = datePickerView.findViewById(R.id.calendarView);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                tv_startDate.setText(date.getDay() + "/" + date.getMonth()+ "/"+ date.getYear());
                tv_endDate .setText(date.getDay() + "/" + date.getMonth()+ "/"+ date.getYear());
            }
        });

        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                CalendarDay startDay = dates.get(0);
                tv_startDate.setText(startDay.getDay() + "/" + startDay.getMonth()+ "/"+ startDay.getYear());
                 startDay = dates.get(dates.size() - 1);
                tv_endDate.setText(startDay.getDay() + "/" + startDay.getMonth()+ "/"+ startDay.getYear());
            }
        });
    }





    public void addEvent(){

        String eventName = edtTxt_eventName.getText().toString();
        int eventCategory  = spinner_category.getSelectedItemPosition();
        //eventColor already setted

        LocalDate startDate = MyEvent.StringToDate( tv_startDate.getText().toString(), '/' );
        LocalDate endDate = MyEvent.StringToDate( tv_endDate.getText().toString(), '/' );
        Time startTime = new Time(tv_startTime.getText().toString(),':');
        Time endTime = new Time(tv_endTime.getText().toString(),':');

        int notification = spinner_notification.getSelectedItemPosition();
        String notes = edtTxt_notes.getText().toString();
        int reccurance = spinner_recurrance.getSelectedItemPosition();

        MyEvent myEvent = new MyEvent(eventName, eventCategory,eventColor,startTime,endTime,startDate,endDate,notification,notes,reccurance);
        Toast.makeText(this, "Etkinlik kaydedildi", Toast.LENGTH_SHORT).show();
    }
}
