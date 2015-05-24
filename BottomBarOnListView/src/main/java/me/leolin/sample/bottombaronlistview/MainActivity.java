package me.leolin.sample.bottombaronlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private List<ProductVo> products = new LinkedList<ProductVo>();
    private Map<String, Integer> productCountMap = new HashMap<String, Integer>();
    private TextView textViewTotalPrice;
    private int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products.add(new ProductVo("1", "ProductA", 100));
        products.add(new ProductVo("2", "ProductB", 200));
        products.add(new ProductVo("3", "ProductC", 300));
        products.add(new ProductVo("4", "ProductD", 400));
        products.add(new ProductVo("5", "ProductE", 500));
        products.add(new ProductVo("6", "ProductF", 600));
        products.add(new ProductVo("7", "ProductG", 700));
        products.add(new ProductVo("8", "ProductH", 800));

        textViewTotalPrice = (TextView) findViewById(R.id.textViewTotalPrice);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new ListViewAdapter());
    }


    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.listitem_product, null);
                viewHolder = new ViewHolder();
                viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textViewProductName);
                viewHolder.textViewPrice = (TextView) convertView.findViewById(R.id.textViewProductPrice);
                viewHolder.textViewCount = (TextView) convertView.findViewById(R.id.textViewProductCount);
                viewHolder.btnAdd = (Button) convertView.findViewById(R.id.buttonAddToCart);
                viewHolder.btnRemove = (Button) convertView.findViewById(R.id.buttonRemoveFromCart);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final ProductVo productVo = products.get(position);

            viewHolder.textViewName.setText(productVo.getName());
            viewHolder.textViewPrice.setText(String.valueOf(productVo.getPrice()));


            Integer count = productCountMap.get(productVo.getId());
            if (count == null) {
                count = 0;
                productCountMap.put(productVo.getId(), count);
            }
            viewHolder.textViewCount.setText(String.valueOf(count));

            viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer count = productCountMap.get(productVo.getId());
                    count = count + 1;
                    productCountMap.put(productVo.getId(), count);
                    viewHolder.textViewCount.setText(String.valueOf(count));

                    totalPrice += productVo.getPrice();
                    textViewTotalPrice.setText(String.valueOf(totalPrice));
                }
            });
            viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer count = productCountMap.get(productVo.getId());
                    if (count == 0) {
                        return;
                    }
                    count = count - 1;
                    productCountMap.put(productVo.getId(), count);
                    viewHolder.textViewCount.setText(String.valueOf(count));

                    totalPrice -= productVo.getPrice();
                    textViewTotalPrice.setText(String.valueOf(totalPrice));
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView textViewName;
            TextView textViewPrice;
            TextView textViewCount;
            Button btnAdd;
            Button btnRemove;
        }
    }


}
