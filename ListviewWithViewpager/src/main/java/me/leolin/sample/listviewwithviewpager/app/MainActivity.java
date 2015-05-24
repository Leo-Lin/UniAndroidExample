package me.leolin.sample.listviewwithviewpager.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;


public class MainActivity extends Activity {

    private ProductRepository productRepository = new ProductRepository();
    private TextView textViewPriceSum;
    private Button buttonGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new CategoryListViewAdapter());

        textViewPriceSum = (TextView) findViewById(R.id.textViewPriceSum);
        buttonGoToCart = (Button) findViewById(R.id.buttonGoToCart);

        buttonGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cart.getProductInCart().clear();
    }

    private class CategoryListViewAdapter extends BaseAdapter {

        private List<CategoryVo> categories = productRepository.getAllCategories();

        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.listitem_category, null);
                viewHolder = new ViewHolder();
                viewHolder.textViewVategoryTitle = (TextView) convertView.findViewById(R.id.textViewCategoryTitle);
                viewHolder.viewPagerProduct = (ViewPager) convertView.findViewById(R.id.productPager);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            CategoryVo categoryVo = categories.get(position);

            viewHolder.textViewVategoryTitle.setText(categoryVo.getName());

            ProductPagerAdapter adapter = new ProductPagerAdapter(categoryVo.getProductVos());
            viewHolder.viewPagerProduct.setAdapter(adapter);

            return convertView;
        }

        private class ViewHolder {

            TextView textViewVategoryTitle;
            ViewPager viewPagerProduct;
        }
    }

    private class ProductPagerAdapter extends PagerAdapter {

        private List<ProductVo> products;

        public ProductPagerAdapter(List<ProductVo> products) {
            this.products = products;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int pagerPosition) {
            View inflate = getLayoutInflater().inflate(R.layout.pageritem_container, null);
            LinearLayout pagerContainer = (LinearLayout) inflate.findViewById(R.id.pagerContainer);

            int start = 3 * pagerPosition + 0;
            int end = 3 * pagerPosition + 2;
            int[] pagerItemProductViewIds = {R.id.pagerItem1, R.id.pagerItem2, R.id.pagerItem3};
            for (int i = start; i <= end; i++) {
                View productView = pagerContainer.findViewById(pagerItemProductViewIds[i - 3 * pagerPosition]);

                ProductVo productVo;
                try {
                    productVo = products.get(i);
                } catch (Exception e) {
                    productView.setVisibility(View.INVISIBLE);
                    continue;
                }


                TextView textViewProductName = (TextView) productView.findViewById(R.id.textViewProductName);
                textViewProductName.setText(productVo.getName());

                TextView textViewProductPrice = (TextView) productView.findViewById(R.id.textViewProductPrice);
                final int price = productVo.getPrice();
                textViewProductPrice.setText(String.valueOf(price));

                final TextView textViewProductCount = (TextView) productView.findViewById(R.id.textViewProductCount);
                final String productId = productVo.getId();


                textViewProductCount.setText(String.valueOf(Cart.getProductCountInCart(productId)));

                Button btnAdd = (Button) productView.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cart.addToCart(productId, 1);

                        textViewProductCount.setText(String.valueOf(Cart.getProductCountInCart(productId)));
                        textViewPriceSum.setText(String.valueOf(Cart.calculateSumPrice()));
                    }
                });

                Button btnDecrease = (Button) productView.findViewById(R.id.btnDecrease);
                btnDecrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cart.addToCart(productId, -1);

                        textViewProductCount.setText(String.valueOf(Cart.getProductCountInCart(productId)));
                        textViewPriceSum.setText(String.valueOf(Cart.calculateSumPrice()));
                    }
                });

            }


            container.addView(inflate);
            return inflate;
        }

        @Override
        public int getCount() {
            return products.size() / 3 + 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view.equals(o);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
