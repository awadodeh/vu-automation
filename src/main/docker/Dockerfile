FROM adidocker-test.ea.vanderbilt.edu/vuit-commons/batch:java8-1.1

# Required at build time
ARG ARTIFACT_ID
ARG ARTIFACT_FILE
ENV ARTIFACT_ID=$ARTIFACT_ID
ENV ARTIFACT_FILE=$ARTIFACT_FILE

# Add artifact
ADD /maven/ $APP_DIR
