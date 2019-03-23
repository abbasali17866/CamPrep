package com.technoweird.camprep;

        import android.app.Activity;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;
        import com.technoweird.camprep.Model.Model;
        import java.util.List;
        import java.util.zip.Inflater;

public class CostomAdapter extends ArrayAdapter<Model> {

    private Activity context;
    List<Model> model;

    public CostomAdapter(Activity context, List<Model> model) {
        super(context, R.layout.item_list_words, model);
        this.context = context;
        this.model = model;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Model models = model.get(position);

        if(convertView==null) {
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.item_list_words,parent,false);

        }


        TextView word = (TextView) convertView.findViewById(R.id.word);
        TextView definitoin = (TextView) convertView.findViewById(R.id.definition);
        TextView synonym = (TextView) convertView.findViewById(R.id.synonymm);
        TextView antonym = (TextView) convertView.findViewById(R.id.antonym);
        TextView example = (TextView) convertView.findViewById(R.id.example);


        word.setText(models.getWord());
        definitoin.setText(models.getDefinition());
        synonym.setText(models.getSynonym());
        antonym.setText(models.getAntonym());
        example.setText(models.getExample());

        return convertView;
    }
}
