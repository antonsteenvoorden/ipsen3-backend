import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Edited by:
 * - Anton
 * - Roger
 */
public class ApiConfiguration extends Configuration implements AssetsBundleConfiguration {
  @NotEmpty
  @JsonProperty
  private String mailUser;

  @NotEmpty
  @JsonProperty
  private String mailPassword;

  @NotEmpty
  @JsonProperty
  private String apiName;

  @JsonProperty("swagger")
  public SwaggerBundleConfiguration swaggerBundleConfiguration;

  /**
   * Container for the database configuration.
   */
  @Valid
  @NotNull
  @JsonProperty
  private DataSourceFactory database = new DataSourceFactory();

  @Valid
  @NotNull
  @JsonProperty
  private final AssetsConfiguration assets = new AssetsConfiguration();

  /**
   * @return the container with the database information
   */
  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() {
    return database;
  }

  public String getApiName() {
    return apiName;
  }

  public String getMailUser() {
    return mailUser;
  }

  public String getMailPassword() {
    return mailPassword;
  }

  @Override
  public AssetsConfiguration getAssetsConfiguration() {
    return assets;
  }
}
