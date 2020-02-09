package io.stacklane.jetbrains;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import io.stacklane.jetbrains.client.UploadClientSettings;

import javax.swing.*;
import java.io.InputStream;
import java.util.Optional;

/**
 *
 */
public final class SLPluginUtil {
    public static final String SL = "Stacklane";
    public static final String SL_RUNNER = "Stacklane Runner";

    /**
     * See:
     *
     * https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html
     */
    public static Icon getIcon(){
        return IconLoader.getIcon("icon16.png", SLRunConfigType.class);
    }

    /**
     * @param project
     *
     * @return {@link Optional#empty()} in the case that either the manifest doesn't exist, or it doesn't define a name.
     */
    public static Optional<String> readManifestName(Project project){
        final VirtualFile manifest = project.getBaseDir().findChild(UploadClientSettings.MANIFEST_FILE_NAME);

        if (manifest == null) return Optional.empty();

        InputStream is = null;

        try {
            is = manifest.getInputStream();

            final String name = UploadClientSettings.readManifestName(is);

            if (name == null) return Optional.empty();

            return Optional.of(name);
        } catch (Throwable throwable) {
            return Optional.empty();
        } finally {
            try {
                is.close();
            } catch (Throwable t){ }
        }
    }

}
