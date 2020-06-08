package br.com.challenge.rest.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import lombok.SneakyThrows;

/**
 * Redirects every page to index.html
 * Used to handle the router
 */
public class PushStateResourceResolver implements ResourceResolver {
    private Resource index = new ClassPathResource("/public/index.html");

    private List<String> handledExtensions = Arrays.asList("html", "js", "json", "css", "eot", "ttf", "woff", "woff2", "appcache", "jpg", "jpeg", "gif", "ico", "png", "svg");
    private List<String> ignoredPaths = Arrays.asList(RestConstants.BASE_REST_V1_PATH);

    private Resource resolve(String requestPath, List<? extends Resource> locations) {
        String extension = StringUtils.getFilenameExtension(requestPath);

        boolean isHandled = handledExtensions.stream().anyMatch(ext -> ext.equals(extension));

        if (ignoredPaths.contains(requestPath))
            return null;

        if (isHandled)
            return locations.stream()
                    .map(loc -> createRelative(loc, requestPath))
                    .filter(res -> res != null && res.exists())
                    .findFirst()
                    .orElse(null);

        return index;
    }

    @SneakyThrows
    private Resource createRelative(Resource resource, String relativePath) {
//        String realPath = relativePath.contains("assets/")
//                ? relativePath.substring(relativePath.indexOf("assets/"))
//                : relativePath;

        return resource.createRelative(relativePath);
    }


    /*
     * Public members
     */

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return resolve(requestPath, locations);
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        Resource resolvedResource = resolve(resourcePath, locations);

        if (resolvedResource == null)
            return null;

        try {
            return resolvedResource.getURL().toString();
        } catch (IOException e) {
            return resolvedResource.getFilename();
        }
    }
}
