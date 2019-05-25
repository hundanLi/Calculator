package club.lzlbog.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public BigDecimal calculate(String s) {
        Deque<BigDecimal> operands = new LinkedList<>();
        Deque<Character> operators = new LinkedList<>();
        HashSet<Character> ops = new HashSet<>(Arrays.asList('+', '-', '×', '÷', '%'));
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (ops.contains(c)) {
                operators.offerLast(c);
            } else if (c != ' ') {
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                for (int j = i + 1; j < chars.length; j++) {
                    char cc = chars[j];
                    if (!ops.contains(cc)) {
                        sb.append(cc);
                        i++;
                    } else {
                        break;
                    }
                }
                BigDecimal opr = new BigDecimal(sb.toString().trim());
                if (!operators.isEmpty() && operators.peekLast() == '×') {
                    operators.pollLast();
                    BigDecimal opl = operands.peekLast();
                    operands.pollLast();
                    operands.offerLast(opl.multiply(opr));
                } else if (!operators.isEmpty() && operators.peekLast() == '÷') {
                    operators.pollLast();
                    BigDecimal opl = operands.peekLast();
                    operands.pollLast();
                    operands.offerLast(opl.divide(opr, 17, RoundingMode.HALF_UP));
                } else if (!operators.isEmpty() && operators.peekLast() == '%') {
                    operators.pollLast();
                    BigDecimal opl = operands.peekLast();
                    operands.pollLast();
                    operands.offerLast(opl.remainder(opr));
                } else {
                    operands.offerLast(opr);
                }
            }
        }
        BigDecimal prod = operands.getFirst();
        operands.pollFirst();
        while (operands.size() > 0) {
            char c = operators.peekFirst();
            operators.pollFirst();
            BigDecimal r = operands.getFirst();
            operands.pollFirst();
            if (c == '+') {
                prod = prod.add(r);
            } else {
                prod = prod.subtract(r);
            }
        }
        return prod;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonDot = findViewById(R.id.buttonDot);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonSub = findViewById(R.id.buttonSub);
        buttonMul = findViewById(R.id.buttonMul);
        buttonDiv = findViewById(R.id.buttonDiv);
        buttonMod = findViewById(R.id.buttonMod);
        buttonEqual = findViewById(R.id.buttonEqual);
        buttonBack = findViewById(R.id.buttonBack);
        buttonClear = findViewById(R.id.buttonClear);
        buttonReset = findViewById(R.id.buttonReset);
        resultTextView = findViewById(R.id.resultTextView);
        expressionTextView = findViewById(R.id.expressionTextView);

        button0.setOnClickListener(buttonClickLister);
        button1.setOnClickListener(buttonClickLister);
        button2.setOnClickListener(buttonClickLister);
        button3.setOnClickListener(buttonClickLister);
        button4.setOnClickListener(buttonClickLister);
        button5.setOnClickListener(buttonClickLister);
        button6.setOnClickListener(buttonClickLister);
        button7.setOnClickListener(buttonClickLister);
        button8.setOnClickListener(buttonClickLister);
        button9.setOnClickListener(buttonClickLister);
        buttonDot.setOnClickListener(buttonClickLister);
        buttonClear.setOnClickListener(buttonClickLister);
        buttonBack.setOnClickListener(buttonClickLister);
        buttonReset.setOnClickListener(buttonClickLister);
        buttonPlus.setOnClickListener(buttonClickLister);
        buttonSub.setOnClickListener(buttonClickLister);
        buttonMul.setOnClickListener(buttonClickLister);
        buttonDiv.setOnClickListener(buttonClickLister);
        buttonMod.setOnClickListener(buttonClickLister);
        buttonEqual.setOnClickListener(buttonClickLister);


        expressionCache = new ExpressionCache(10);
        historySpinner = findViewById(R.id.historySpinner);
        historySpinner.setPromptId(R.string.history);
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, expressionCache);
        historySpinner.setAdapter(adapter);
        historySpinner.setOnItemSelectedListener(onItemSelectedListener);
        historySpinner.setOnTouchListener(onTouchListener);

    }

    private String format(String s) {
        if (s.indexOf(".") > 0) {
            //去掉后面无用的零
            s = s.replaceAll("0+?$", "");
            //如小数点后面全是零则去掉小数点
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }

    private View.OnClickListener buttonClickLister = new View.OnClickListener() {
        Button button;
        Set<Character> operatorSet = new HashSet<>(Arrays.asList('+', '-', '×', '÷', '%'));

        @Override
        public void onClick(View v) {
            button = (Button) v;
            switch (v.getId()) {
                case R.id.button0:

                case R.id.button1:

                case R.id.button2:

                case R.id.button3:

                case R.id.button4:

                case R.id.button5:

                case R.id.button6:

                case R.id.button7:

                case R.id.button8:

                case R.id.button9:
                    expression.append(button.getText());
                    expressionTextView.setText(expression);
                    try {
                        // 实时计算结果
                        String exp = expression.toString();
                        BigDecimal result = calculate(exp);

                        // 显示
                        String value = String.valueOf(result);
                        resultTextView.setText(format(value));
                        expressionTextView.setText(exp);

                    } catch (Exception e) {
                        resultTextView.setText(getString(R.string.error));
                    }
                    break;

                case R.id.buttonDot:
                    if (expression.length() == 0 ||
                            operatorSet.contains(expression.charAt(expression.length() - 1))) {
                        expression.append("0");
                    } else if (expression.charAt(expression.length() - 1) == '.') {
                        expression.deleteCharAt(expression.length() - 1);
                    }
                    int lastIndexOfDot = expression.lastIndexOf(".");
                    int i;
                    for (i = lastIndexOfDot; i >= 0 && i < expression.length(); i++) {
                        if (operatorSet.contains(expression.charAt(i))) {
                            break;
                        }
                    }
                    if (i < 0 || i < expression.length()) {
                        expression.append('.');
                        expressionTextView.setText(expression);
                    }
                    break;

                case R.id.buttonPlus:

                case R.id.buttonSub:

                case R.id.buttonMul:

                case R.id.buttonDiv:

                case R.id.buttonMod:
                    if (expression.length() == 0) {
                        return;
                    }
                    if (operatorSet.contains(expression.charAt(expression.length() - 1))) {
                        expression.deleteCharAt(expression.length() - 1);
                    } else if (expression.charAt(expression.length() - 1) == '.') {
                        expression.deleteCharAt(expression.length() - 1);
                    }
                    expression.append(button.getText());
                    expressionTextView.setText(expression);
                    break;

                case R.id.buttonBack:
                    if (expression.length() == 0) {
                        return;
                    }
                    expression.deleteCharAt(expression.length() - 1);
                    expressionTextView.setText(expression);
                    break;

                case R.id.buttonClear:
                    if (expression.length() > 0) {
                        expression.delete(0, expression.length());
                    }
                    expressionTextView.setText(R.string.expression);
                    break;
                //计算表达式的值并缓存起来
                case R.id.buttonEqual:
                    try {
                        String exp = expression.toString();
                        BigDecimal result = calculate(exp);
                        // 去除尾部的冗余零
                        String value = format(String.valueOf(result));

                        //添加到历史记录
                        expressionCache.remove(exp);
                        expressionCache.addFirst(exp);
                        expressionCache.putResult(exp,value);

                        // 通知spinner刷新数据显示
                        adapter.notifyDataSetChanged();
                        historySpinner.setSelection(0, true);

                        // 显示
                        resultTextView.setText(value);
                        expressionTextView.setText(exp);

                    } catch (Exception e) {
                        resultTextView.setText(getString(R.string.error));
                    }

                    break;

                case R.id.buttonReset:
                    expression.delete(0, expression.length());
                    expressionCache.clear();
                    adapter.notifyDataSetChanged();
                    resultTextView.setText(R.string.result);
                    expressionTextView.setText(R.string.expression);
                    break;
                default:
                    break;
            }
        }
    };

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String exp = expressionCache.get(position);
            expression = new StringBuilder(exp);
            expressionTextView.setText(exp);
            String result = expressionCache.getResult(exp);
            resultTextView.setText(result);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Class<?> viewClass = AdapterView.class;
            try {
                Field field = viewClass.getDeclaredField(getString(R.string.mOldSelectedPosition));
                field.setAccessible(true);
                field.setInt(v, Integer.MIN_VALUE);
                v.performClick();
            } catch (Exception e) {
                Log.e("反射抛出异常", e.getMessage());
            }
            return false;
        }
    };


    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;
    private Button buttonPlus;
    private Button buttonSub;
    private Button buttonMul;
    private Button buttonDiv;
    private Button buttonMod;
    private Button buttonDot;
    private Button buttonEqual;
    private Button buttonBack;
    private Button buttonClear;
    private Button buttonReset;
    private TextView resultTextView;
    private TextView expressionTextView;
    private Spinner historySpinner;
    private ArrayAdapter<String> adapter;
    private ExpressionCache expressionCache;
    private StringBuilder expression = new StringBuilder();

}
