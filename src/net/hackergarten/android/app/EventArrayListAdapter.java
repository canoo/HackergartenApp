package net.hackergarten.android.app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.hackergarten.android.app.model.Event;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;


public class EventArrayListAdapter implements ListAdapter, Filterable {
	
	// TODO add filtering
	
	static final int EVENT_ROW_VIEWTYPE = 0;
	
	static DecimalFormat distanceFormat = new DecimalFormat("#0.0 km");
	
	private List<Event> entries;

	private final ArrayList<DataSetObserver> observer = new ArrayList<DataSetObserver>();
	
	private final SubjectFilter titleFilter = new SubjectFilter();
		
	private final LayoutInflater layoutInflater;
	
	static class ViewHolder {
		TextView subject;
		TextView description;
		TextView date;
	}

	public EventArrayListAdapter(Context context, LayoutInflater layoutInflater) {
		this(context, layoutInflater, new ArrayList<Event>());
	}
	
	public EventArrayListAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Event> entries) {
		this.layoutInflater = layoutInflater;
		this.entries = entries;
	}
	
	public void setEntries(List<Event> entries) {
		this.entries = entries;
		notifyObserversChanged();
	}

	// Adapter impl.
	
	public int getCount() {
		return entries.size();
	}

	public Object getItem(int position) {
		return entries.get(position);
	}

	public long getItemId(int position) {
		return entries.get(position).getIdAsLong();
	}

	public int getItemViewType(int position) {
		// we have only one view type
		return EVENT_ROW_VIEWTYPE;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) { 
			convertView = layoutInflater.inflate(R.layout.eventlist_item, parent, false);
			holder = new ViewHolder();
			holder.subject = (TextView) convertView.findViewById(R.id.eventSubject);
			holder.description = (TextView) convertView.findViewById(R.id.eventDescription);
			holder.date = (TextView) convertView.findViewById(R.id.eventDate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Event event = entries.get(position);
		holder.subject.setText(event.getSubject());
		holder.description.setText(event.getDescription());
		holder.date.setText(event.getTimeUST().toLocaleString());
		return convertView;
	}
	
	public int getViewTypeCount() {
		return 1;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}

	private void notifyObserversChanged() {
		for (DataSetObserver dso : observer) {
			dso.onChanged();
		}
	}
	
	public void registerDataSetObserver(DataSetObserver observer) {
		this.observer.add(observer);
	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
		this.observer.remove(observer);
	}
	
	// ListAdapter impl.
	
	public boolean areAllItemsEnabled() {
		return true;
	}
	
	public boolean isEnabled(int position) {
		// TODO might be relevant for filtering ?
		return true;
	}

	// Filterable impl.
	
	public Filter getFilter() {
		return titleFilter;
	}
	
	private final class SubjectFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults result = new FilterResults();
			LinkedList<Event> matches = new LinkedList<Event>();
			String lcConstraint = constraint.toString().toLowerCase();
			for (Event entry : entries) {
				if (entry.getSubject().toLowerCase().startsWith(lcConstraint)) {
					matches.add(entry);
				}
			}
			result.values = matches.toArray();
			result.count = matches.size();
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// TODO
		}
		
	}

}
