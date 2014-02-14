package nth.ant.manifest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class AntManifest extends Task {
	private static final String MANIFEST_VERSION = "Manifest-Version";
	private static final String DOT = ".";
	private static final String IMPLEMENTATION_TITLE = "Implementation-Title";
	private static final String IMPLEMENTATION_VERSION = "Implementation-Version";
	private static final String IMPLEMENTATION_VENDOR = "Implementation-Vendor";
	private static final String BUILD_BY = "Built-By";
	private static final String BUILD_DATE = "Built-Date";
	private static final String CREATED_BY = "Created-By";
	private File manifestFile;
	private String buildBy;
	private Date builtDate;
	private String createdBy;
	private String title;
	private int major = 0;
	private int minor = 0;
	private int build = 0;
	private Properties properties;

	@Override
	public void init() throws BuildException {
		String userName = System.getProperty("user.name");
		createdBy = userName;
		buildBy = userName;
		builtDate = new Date();

	}

	public void setManifestFile(File manifestFile) {
		this.manifestFile = manifestFile;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public void setBuild(int build) {
		this.build = build;
	}

	public void setBuildBy(String buildBy) {
		this.buildBy = buildBy;
	}

	public void setBuiltDate(Date builtDate) {
		this.builtDate = builtDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void execute() throws BuildException {
		super.execute();
		// load defaults from manifest file
		properties = new Properties();
		try {
			properties.load(new FileInputStream(manifestFile));
		} catch (Exception e) {
			// failed, no big deal;
		}
		String version = properties.getProperty(IMPLEMENTATION_VERSION, "0.0.0");
		StringTokenizer tokenizer = new StringTokenizer(version, DOT);
		if (major == 0) {
			major = Integer.parseInt(tokenizer.nextToken());
		}
		if (minor == 0) {
			minor = Integer.parseInt(tokenizer.nextToken());
		}
		if (build == 0) {
			build = Integer.parseInt(tokenizer.nextToken()) + 1;
		}

		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		properties.setProperty(MANIFEST_VERSION, "1.0");
		properties.setProperty(CREATED_BY, createdBy);
		properties.setProperty(BUILD_BY, buildBy);
		properties.setProperty(BUILD_DATE, dateFormater.format(builtDate));
		if (title==null) {
			title=getProject().getName();
		}
		properties.setProperty(IMPLEMENTATION_TITLE, title);
		properties.setProperty(IMPLEMENTATION_VERSION, major + DOT + minor + DOT + build);
		properties.setProperty(IMPLEMENTATION_VENDOR, createdBy);
		
		try {
			FileWriter fstream = new FileWriter(manifestFile);
			BufferedWriter out = new BufferedWriter(fstream);
			for (Enumeration<?> e = properties.keys(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				String val = (String) properties.get(key);
				out.write(key + ": " + val);
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}

}
