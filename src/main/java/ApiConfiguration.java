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

  /**
   * Haalt de API naam op uit het opgegeven .yml bestand
   * @return String apiName
     */
  public String getApiName() {
    return apiName;
  }
  /**
   * Haalt de EMail gebruikersnaam op uit het opgegeven .yml bestand
   * @return String mailUser
   */
  public String getMailUser() {
    return mailUser;
  }
  /**
   * Haalt het Email wachtwoord op uit het opgegeven .yml bestand
   * @return String mailPassword
   */
  public String getMailPassword() {
    return mailPassword;
  }

  @Override
  public AssetsConfiguration getAssetsConfiguration() {
    return assets;
  }
}
