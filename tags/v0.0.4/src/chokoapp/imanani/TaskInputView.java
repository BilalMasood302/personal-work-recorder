package chokoapp.imanani;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class TaskInputView extends RelativeLayout {
    private LayoutInflater inf;
    private OnTaskChangedListener listener;

    private Button changeTaskButton;
    private TaskCompleteView taskCompleteView;
    private EditText taskDescriptionInput;

    public TaskInputView(Context context) {
        this(context, null, 0);
    }

    public TaskInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View topLayout = inf.inflate(R.layout.task_input, this);

        taskCompleteView = (TaskCompleteView)topLayout.findViewById(R.id.taskCompleteView);
        taskDescriptionInput = (EditText)topLayout.findViewById(R.id.taskDescriptionInput);
        changeTaskButton = (Button)topLayout.findViewById(R.id.changeTaskButton);

        taskCompleteView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TaskCompleteAdapter adapter = (TaskCompleteAdapter)parent.getAdapter();
                    taskDescriptionInput.setText(adapter.getItem(position).getDescription());
                }
            });
        taskCompleteView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TaskCompleteAdapter adapter = (TaskCompleteAdapter)parent.getAdapter();
                    taskDescriptionInput.setText(adapter.getItem(position).getDescription());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    taskDescriptionInput.setText("");
                }
            });

        changeTaskButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if ( taskCompleteView.length() == 0 ) return;

                    String code = taskCompleteView.getText().toString();
                    String description = taskDescriptionInput.getText().toString();
                    Task task = new Task(code, description);
                    TaskCompleteAdapter adapter = (TaskCompleteAdapter)taskCompleteView.getAdapter();
                    adapter.add(task);
                    if ( listener != null ) {
                        listener.onChanged(task);
                    }
                }
            });
    }

    public void setAdapter(TaskCompleteAdapter adapter) {
        taskCompleteView.setAdapter(adapter);
    }

    public void setOnTaskChangedListener(OnTaskChangedListener l) {
        this.listener = l;
    }

    interface OnTaskChangedListener {
        void onChanged(Task task);
    }
}
