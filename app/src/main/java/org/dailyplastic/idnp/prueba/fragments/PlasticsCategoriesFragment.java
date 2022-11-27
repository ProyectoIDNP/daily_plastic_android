package org.dailyplastic.idnp.prueba.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.dailyplastic.idnp.R;
import org.dailyplastic.idnp.prueba.adapters.CategoryGridViewAdapter;
import org.dailyplastic.idnp.prueba.constants.Constants;
import org.dailyplastic.idnp.prueba.interfaces.CategoryService;
import org.dailyplastic.idnp.prueba.model.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlasticsCategoriesFragment extends Fragment {

    GridView categoriesGridView;
    List<Category> categories;
    CategoryService categoryService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View plasticCategoriesFragment = inflater.inflate(R.layout.fragment_plastics_categories, container, false);


        //Obtener los datos del api rest
        getAll();

        categoriesGridView = plasticCategoriesFragment.findViewById(R.id.gridViewCategories);
        ArrayList<Category> categoriesModelArrayList = new ArrayList<Category>();

        categoriesModelArrayList.add(new Category(1, "Categoria 1", "10/12/2022", "34/35/2009"));
        categoriesModelArrayList.add(new Category(2, "Categoria 2", "10/12/2022", "34/35/2009"));
        categoriesModelArrayList.add(new Category(3, "Categoria 3", "10/12/2022", "34/35/2009"));
        categoriesModelArrayList.add(new Category(4, "Categoria 4", "10/12/2022", "34/35/2009"));
        categoriesModelArrayList.add(new Category(5, "Categoria 5", "10/12/2022", "34/35/2009"));
        categoriesModelArrayList.add(new Category(6, "Categoria 6", "10/12/2022", "34/35/2009"));

        CategoryGridViewAdapter adapter = new CategoryGridViewAdapter(getActivity(), categoriesModelArrayList);

        categoriesGridView.setAdapter(adapter);

        return plasticCategoriesFragment;
    }

    private void getAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        categoryService = retrofit.create(CategoryService.class);
        Call<List<Category>> call = categoryService.getAll();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                //si falla el response
                if(!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                categories = response.body();
                //Colocacion de los datos en el gridView
                /*CategoryGridViewAdapter adapter = new CategoryGridViewAdapter(getActivity(), categories);
                categoriesGridView.setAdapter(adapter);*/

                categories.forEach(c -> Log.i("Categorias: ", c.toString()));
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }
}