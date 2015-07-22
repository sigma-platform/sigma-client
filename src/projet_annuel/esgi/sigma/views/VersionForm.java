package projet_annuel.esgi.sigma.views;

import org.json.JSONException;
import org.json.JSONObject;
import projet_annuel.esgi.sigma.WebService;
import projet_annuel.esgi.sigma.models.User;
import projet_annuel.esgi.sigma.models.Version;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class VersionForm implements ActionListener {
    private Integer projectId;
    private Version selectedVersion;

    public JFrame frame;
    private JTextField labelTextField;
    private JTextArea descriptionTextArea;
    private JTextField dateStartTextField;
    private JTextField dateEndTextField;
    private JButton addVersionButton;
    private JPanel versionPanel;

    public VersionForm(Integer projectId, Version version) {
        this.projectId = projectId;
        this.selectedVersion = version;

        if(selectedVersion != null) {
            labelTextField.setText(selectedVersion.getLabel());
            descriptionTextArea.setText(selectedVersion.getDescription());
            dateStartTextField.setText(selectedVersion.getDateStart());
            dateEndTextField.setText(selectedVersion.getDateEnd());
            addVersionButton.setText("Save");
        }

        addVersionButton.addActionListener(this);
    }

    public void init() {
        frame = new JFrame();

        if (selectedVersion == null)
            frame.setTitle("New version");
        else
            frame.setTitle("Edit version");

        frame.setContentPane(this.versionPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(320, 400);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(addVersionButton)) {
            if(selectedVersion == null)
                new PostVersion().execute();
            else
                new UpdateVersion().execute();
        }
    }

    private HashMap<String, Object> getVersionPostMap() {
        HashMap<String, Object> postParams = new HashMap<String, Object>();
        postParams.put("label", labelTextField.getText());
        postParams.put("description", descriptionTextArea.getText());
        postParams.put("date_start", dateStartTextField.getText());
        postParams.put("date_end", dateEndTextField.getText());
        postParams.put("project_id", projectId);

        return postParams;
    }

    class PostVersion extends SwingWorker {
        protected Object doInBackground() throws Exception {
            WebService webService = new WebService();
            HashMap<String, String> getParams = new HashMap<String, String>();
            getParams.put("token", User.getInstance().getToken());

            HashMap<String, Object> postParams = VersionForm.this.getVersionPostMap();

            return webService.call(WebService.POST_METHOD, WebService.STORE_VERSION_URI, getParams, postParams);
        }

        @Override
        protected void done() {
            super.done();
            try {
                JSONObject result = (JSONObject) get();
                if(result.getBoolean("success")) {
                    VersionForm.this.frame.dispose();
                    new Toast(result.getString("message"), 5000).setVisible(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class UpdateVersion extends SwingWorker {
        protected Object doInBackground() throws Exception {
            WebService webService = new WebService();

            HashMap<String, String> getParams = new HashMap<String, String>();
            getParams.put("token", User.getInstance().getToken());
            getParams.put("id", selectedVersion.getId().toString());

            HashMap<String, Object> postParams = VersionForm.this.getVersionPostMap();

            return webService.call(WebService.PUT_METHOD, WebService.VERSION_URI, getParams, postParams);
        }

        @Override
        protected void done() {
            super.done();
            try {
                JSONObject result = (JSONObject) get();
                if(result.getBoolean("success")) {
                    VersionForm.this.frame.dispose();
                    new Toast(result.getString("message"), 5000).setVisible(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
