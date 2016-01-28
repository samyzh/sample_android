package com.zsy.frame.sample.control.android.a26setting.cloud.printer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.zsy.frame.sample.R;

/**
 * This class demonstrates how to implement HTML content printing from a
 * {@link WebView} which is not shown on the screen and print PDF file in local
 * of cell phone.
 * 
 * @author Kinny.Qin 2014-06-27
 */
public class PrintHtmlOffScreenAct extends Activity implements OnClickListener {
	private static final String TAG = PrintHtmlOffScreenAct.class.getSimpleName();
	private Button btnButtonHtml;
	private Button btnButtonPdf;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a26setting_cloud_printer_cloudprinter_printhtml);
		btnButtonHtml = (Button) findViewById(R.id.btnprinthtml);
		btnButtonPdf = (Button) findViewById(R.id.btnprintpdf);
		btnButtonHtml.setOnClickListener(this);
		btnButtonPdf.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		int view_id = v.getId();
		switch (view_id) {
			case R.id.btnprinthtml:// print html
				print();
				break;
			case R.id.btnprintpdf:// print pdf
				printPDF();
				break;
			default:
				break;
		}
	}

	private void print() {
		// Create a WebView and hold on to it as the printing will start when
		// load completes and we do not want the WbeView to be garbage
		// collected.
		mWebView = new WebView(this);
		// Important: Only after the page is loaded we will do the print.
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				doPrint();
			}
		});
		// Load an HTML page.
		mWebView.loadUrl("file:///android_res/raw/PrintHtmlOffScreenAct.mht");
	}

	private void doPrint() {
		// Get the print manager.
		PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

		// Create a wrapper PrintDocumentAdapter to clean up when done.
		PrintDocumentAdapter adapter = new PrintDocumentAdapter() {
			private final PrintDocumentAdapter mWrappedInstance = mWebView.createPrintDocumentAdapter();

			@Override
			public void onStart() {
				mWrappedInstance.onStart();
			}

			@SuppressLint("WrongCall") @Override
			public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
				mWrappedInstance.onLayout(oldAttributes, newAttributes, cancellationSignal, callback, extras);
			}

			@Override
			public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
				mWrappedInstance.onWrite(pages, destination, cancellationSignal, callback);
			}

			@Override
			public void onFinish() {
				mWrappedInstance.onFinish();
				// Intercept the finish call to know when printing is done
				// and destroy the WebView as it is expensive to keep around.
				mWebView.destroy();
				mWebView = null;
			}
		};

		// Pass in the ViewView's document adapter.
		printManager.print("MotoGP stats", adapter, null);
	}



	/**
	 * Print PDF files in local
	 */
	public void printPDF() {
		Log.e(TAG, "printPDF");
		PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
		String jobName = this.getString(R.string.app_name) + "Document";
		/**
		 * Base class that provides the content of a document to be printed.
		 * <P>
		 * you can see this API at web site
		 * https://developer.android.com/reference
		 * /android/print/PrintDocumentAdapter.html
		 * <P>
		 */
		PrintDocumentAdapter pda = new PrintDocumentAdapter() {
			@Override
			public void onStart() {// Called when printing start.
				super.onStart();
			}

			@Override
			public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
				InputStream input = null;
				OutputStream output = null;
				try {
					// The file need to print in cell phone
					input = new FileInputStream("/storage/emulated/0/test.pdf");
					output = new FileOutputStream(destination.getFileDescriptor());
					byte[] buf = new byte[1024];
					int bytesRead;
					while ((bytesRead = input.read(buf)) > 0) {
						output.write(buf, 0, bytesRead);
					}
					callback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });

				}
				catch (FileNotFoundException ee) {
					ee.getStackTrace();
					// Catch exception
				}
				catch (Exception e) {
					e.getStackTrace();
					// Catch exception
				}
				finally {
					try {
						if (input != null) {
							input.close();
						}
						if (output != null) {
							output.close();
						}
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
				Log.e(TAG, "onLayout");
				if (cancellationSignal.isCanceled()) {
					callback.onLayoutCancelled();
					return;
				}
				// int pages = computePageCount(newAttributes);

				PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("test.pdf").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
				callback.onLayoutFinished(pdi, true);
			}

		};

		printManager.print(jobName, pda, null);
	}

	// Get Sdcard root path
	public void getSdcardRootPath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Log.e(TAG, "can read sdcard content");
			File sdCardDir = Environment.getExternalStorageDirectory();
			Log.e(TAG, "sdCardDir: " + sdCardDir.getPath());
		}
	}

	/**
	 * use cloud print app to print pdf
	 * 
	 * @param content
	 */
	public void printViaGoogleCloudPrintApp(Uri content) {
		Intent printIntent = new Intent(Intent.ACTION_SEND);
		printIntent.setPackage("com.google.Android.apps.cloudprint");
		printIntent.setType("pdf/*");
		printIntent.putExtra(Intent.EXTRA_TITLE, "Print Test Title");
		printIntent.putExtra(Intent.EXTRA_STREAM, content);
		startActivity(printIntent);
	}

}