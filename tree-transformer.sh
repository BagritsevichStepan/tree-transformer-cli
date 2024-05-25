if [ -z "$1" ]
  then
    java -jar tree-transformer-cli-1.0.jar help
  else
    java -jar tree-transformer-cli-1.0.jar "$@"
fi
