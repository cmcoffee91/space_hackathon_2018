package com.yellowspace.coffee.yellowspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String mTag = "";

    private SearchView searchView;
    private List<Feed> feedList;
    private RecyclerView mVideoRecyclerView;

    private FeedAdapter mVideoAdapter;

    private OnFragmentInteractionListener mListener;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = (SearchView) v.findViewById(R.id.searchView);

        feedList = new ArrayList<>();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //  Log.d("searchQuerySubmit","query is : " + query);
                lookForMember(query);
                closeKeyboards();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Log.d("searchQueryOnTextChange","query textchange is : " + newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //Log.d("search","search closed");

                lookForMember("");

                return false;
            }
        });

        mVideoRecyclerView = (RecyclerView) v.findViewById(R.id.feed_recycler_view);
        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVideoAdapter = new FeedAdapter(feedList, getActivity());
        mVideoRecyclerView.setAdapter(mVideoAdapter);

        new TheTask().execute();

        mVideoAdapter.notifyDataSetChanged();


        return v;
    }

    private void lookForMember(String lookUpString)
    {

        feedList.clear();


        mTag = lookUpString;
        new TheTask().execute();
    }

    private class TheTask extends AsyncTask<Void,Void,Void>
    {

        Feed displaylist;
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Loading ");
            dialog.setMessage("Loading your space stuff");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try
            {

                String url = "https://images-api.nasa.gov/search?q=" + mTag + "&media_type=image";

                String response = getUrlString(url);

                JSONObject json = new JSONObject(response.toString());
                Log.e("feedFrag", "response: " + response.toString());





                JSONArray jsonArray = json.getJSONObject("collection").getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                 //   Log.e("feedFrag", "jsonarray objectdat: " + jsonObject);

                    String imageUrl = "";

                    String description = "";


                    JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);

                    if(data.has("description"))
                    {

                        description = data.getString("description");
                    }
                    else{
                        Log.e("feedFrag", "doesnt have json object: ");
                    }

                   imageUrl = jsonObject.getJSONArray("links").getJSONObject(0).getString("href");



                    feedList.add(new Feed(imageUrl, description));
                }








            }
            catch(Exception e1)
            {
                e1.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            mVideoAdapter.notifyDataSetChanged();

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }

    }



    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    private String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    private void closeKeyboards()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);


        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
