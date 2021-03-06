package me.leolin.sample.pagertitlestrip.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import me.leolin.sample.pagertitlestrip.app.volley.VolleyQueue;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leolin
 */
public class ProductListFragment extends Fragment {
    private CategoryListViewAdapter categoryListViewAdapter;
    private TextView textViewPriceSum;
    private ListView listView;
    private ProgressBar progressBar;
    private String apiKey = "TimothyApi";
    private ProductRepository productRepository = new ProductRepository();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        listView = (ListView) view.findViewById(R.id.listview);
        textViewPriceSum = (TextView) view.findViewById(R.id.textViewPriceSum);
        loadProducts();
    }


    private void loadProducts() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest("http://jasonchi.ddns.net:8080/api/product", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                ProductRepository.refreshData(response);
                loadCombo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ApiKey", apiKey);
                return map;
            }
        };
        request.setTag("product");

        VolleyQueue.getInstance(getActivity()).getRequestQueue().add(request);
    }

    private void loadCombo() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest("http://jasonchi.ddns.net:8080/api/Combo", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                ComboRepository.refreshData(response);
                render();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ApiKey", apiKey);
                return map;
            }
        };
        request.setTag("product");

        VolleyQueue.getInstance(getActivity()).getRequestQueue().add(request);
    }

    private void render() {
        categoryListViewAdapter = new CategoryListViewAdapter();
        listView.setAdapter(categoryListViewAdapter);
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.listitem_category, null);
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
            View inflate = getActivity().getLayoutInflater().inflate(R.layout.pageritem_container, null);
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

                        //檢查套餐
                        checkCombo();

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

    private void checkCombo() {
        final String availableComboId = Cart.getAvailableComboId();
        if (availableComboId != null) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("符合套餐")
                    .setMessage("是否要升級為套餐？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Cart.addToCart(availableComboId, 1);

                            ComboVo comboVo = ComboRepository.getCombosMap().get(availableComboId);
                            for (ComboDetailVo detailVo : comboVo.getDetails()) {
                                Cart.addToCart(detailVo.getProductId(), 0 - detailVo.getQuantity());
                            }

                            //更新商品數量
                            categoryListViewAdapter.notifyDataSetChanged();

                            //更新總數
                            textViewPriceSum.setText(String.valueOf(Cart.calculateSumPrice()));

                            //繼續檢查
                            checkCombo();
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }

}
