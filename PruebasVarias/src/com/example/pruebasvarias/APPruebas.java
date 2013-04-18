package com.example.pruebasvarias;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class APPruebas extends Activity {
	private final static int CT_SEGURO_BORRAR = 1;
	private final static int CT_CAMBIA_TEXTO = 2;

	private final static int CT_REQUEST_SEGUNDA = 1;
	public final static String CT_SEGUNDA_RESULTADO = "extra_result";
	public final static int CT_RESULT_SEMIRESULTADO = RESULT_FIRST_USER + 1;

	TextView mTVTexto;
	EditText mETTextoDialogo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ap_pruebas);

		mTVTexto = (TextView) findViewById(R.id.ap_tv_texto);
		registerForContextMenu(mTVTexto);

		ponTexto("Texto inicial");

		findViewById(R.id.ap_bt_navegar).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// irAGoogle();
						// abrirSegunda();
						// llamar();
						accederContactos();
					}
				});
	}

	protected void ponTexto(String s) {
		mTVTexto.setText(s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.menu.ap_pruebas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ap_mn_hola:
			showDialog(CT_CAMBIA_TEXTO);
			break;
		case R.id.ap_mn_adios:
			ponTexto("Adios");
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	int mContador = 0;

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Toast.makeText(this, "onPrepareOptionsMenu invocado",
				Toast.LENGTH_SHORT).show();
		mContador++;
		menu.findItem(R.id.ap_mn_adios).setEnabled(mContador % 2 == 0);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v == mTVTexto) {
			MenuInflater inf = getMenuInflater();
			inf.inflate(R.menu.ap_contextual_texto, menu);
		}

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ap_mn_borrar:
			showDialog(CT_SEGURO_BORRAR);
			break;
		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case CT_SEGURO_BORRAR:
			// AlertDialog.Builder bldr = new AlertDialog.Builder(this);
			// bldr.setTitle("Borrar");
			// bldr.setMessage("Seguro que quiere borrar el texto?");
			// bldr.setPositiveButton("Borrar", new
			// DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// ponTexto("");
			// }
			// });
			// bldr.setNegativeButton("Cancelar", null);
			// return bldr.create();
			DatePickerDialog dlg = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							Toast.makeText(
									APPruebas.this,
									"Se ha seleccionado una fecha del año "
											+ year, Toast.LENGTH_LONG).show();
						}
					}, 2013, 3, 11);
			return dlg;
		case CT_CAMBIA_TEXTO:
			AlertDialog.Builder bldr2 = new AlertDialog.Builder(this);
			bldr2.setTitle("Cambio texto")
					.setMessage("Introduzca el texto para el primer textview: ")
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									ponTexto(mETTextoDialogo.getText()
											.toString());
								}
							}).setNegativeButton("Cancelar", null);
			// Incrustar layout propio
			LayoutInflater inf = getLayoutInflater();
			View v = inf.inflate(R.layout.ap_dialogo_texto, null);
			mETTextoDialogo = (EditText) v.findViewById(R.id.ap_et_dlg_texto);
			bldr2.setView(v);
			return bldr2.create();
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		switch (id) {
		case CT_CAMBIA_TEXTO:
			mETTextoDialogo.setText(mTVTexto.getText());
			// Si no tuviera la variable miembro podría hacer:
			// ((EditText)dialog.findViewById(R.id.ap_et_dlg_texto)).setText(mTVTexto.getText());
		default:
			super.onPrepareDialog(id, dialog, args);
		}
	}

	protected void irAGoogle() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.google.com"));
		startActivity(intent);
	}

	protected void abrirSegunda() {
		Intent intent = new Intent("com.example.pruebasvarias.segundaactividad");
		startActivityForResult(intent, CT_REQUEST_SEGUNDA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CT_REQUEST_SEGUNDA:
			if (resultCode == RESULT_CANCELED)
				Toast.makeText(this, "La acitividad se canceló",
						Toast.LENGTH_SHORT).show();
			else if (resultCode == RESULT_OK) {
				Toast.makeText(
						this,
						"La acitividad devolvió el resultado: "
								+ data.getStringExtra(CT_SEGUNDA_RESULTADO),
						Toast.LENGTH_SHORT).show();
			} else if (resultCode == CT_RESULT_SEMIRESULTADO) {

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void llamar() {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:666999666"));
		startActivity(intent);
	}

	protected void accederContactos() {
		Cursor cur = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		startManagingCursor(cur);

		String[] result = new String[cur.getCount()];

		for (boolean hasData = cur.moveToFirst(); hasData; hasData = cur
				.moveToNext()) {
			int nameidx = cur
					.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
			int Ididx = cur
					.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
			String strName = cur.getString(nameidx);
			String strId = cur.getString(Ididx);
			String phones = accederTelefonosPorContacto(strId);

			result[cur.getPosition()] = strName + "(" + strId + ")" + phones;
		}
		stopManagingCursor(cur);
	}

	protected String accederTelefonosPorContacto(String id) {
		String ret = "";
		Cursor cur = getContentResolver()
				.query(Data.CONTENT_URI,
						new String[] { Data._ID, Phone.NUMBER, Phone.TYPE,
								Phone.LABEL },
						Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
								+ Phone.CONTENT_ITEM_TYPE + "'",
						new String[] { id }, null);
		for (boolean hasData = cur.moveToFirst(); hasData; hasData = cur.moveToNext()) {
			String phone = cur.getString(cur.getColumnIndex(Phone.NUMBER));
			ret += " **" + phone + "** ";
		}
		return ret;
	}
}
