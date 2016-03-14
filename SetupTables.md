## アプリケーションテスト時にテーブルの初期登録をする方法 ##
### 動機 ###
アプリケーションを単純にコンパイルして、エミュレータで実行すると最初はデータ0件でスタートします。

作業一覧はまだしも、作業記録を画面からいちいち登録してテストするのは面倒。

また、アプリケーションバグにより、思わぬデータが登録されてしまう場合もあり(実際あった)、そのような場合には「一からやり直したい」

作業をいくつか登録して、日々の作業記録を数日取った状態のレコードを用意してテーブルに初期登録する仕組みが欲しい。

Javaのソースコードに書くのはイヤ。そもそもテストデータなんだし。

### どうするの？ ###
アプリケーションは 0件でスタートして、画面から手動で登録作業を実施するのではなく、数件のテストデータをロードするためのシェルスクリプトを用意して、これを実行する。

最初に一回実行すれば、エミュレータを終了してもデータは残るので、次の日も同じデータでテストを実行できる。

何らかの問題が起こって、一からやり直す場合は、再度シェルスクリプトを実行すれば最初の状態に戻れる。

### 具体的な手順 ###
  1. エミュレータを起動する(お好きな方法で)
  1. <project root>/scripts/setup\_tables.sh をエミュレータにインストール
> > `$ adb push scripts/setup_tables.sh /data/data/chokoapp.imanani/databases/`
  1. エミュレータにログイン
> > `$ adb shell`
  1. インストール先に移動
> > `# cd /data/data/chokoapp.imanani/databases`
  1. スクリプトを実行
> > `# sh setup_tables.sh`

※前提※ android-sdk をインストールしたディレクトリの tools サブディレクトリと platform-tools サブディレクトリにパスが通っていること

### 登録内容の確認 ###
初期セットアップした内容だけでなく、アプリケーションテスト実施中にデータベースの更新内容を確認したいが、毎回 `select *` を入力するのは面倒。

おおよそ、入力するのは `select * from hoge;` に決まっているので、これもシェルスクリプトとして事前に用意することで readline の無い苦しみから若干開放される。

方法はさっきとほぼ同じ
  1. エミュレータを起動する(お好きな方法で)
  1. <project root>/scripts/display\_tables.sh をエミュレータにインストール
> > `$ adb push scripts/display_tables.sh /data/data/chokoapp.imanani/databases/`
  1. エミュレータにログイン
> > `$ adb shell`
  1. インストール先に移動
> > `# cd /data/data/chokoapp.imanani/databases`
  1. スクリプトを実行
> > `# sh display_tables.sh`

display\_tables.sh は例えば tm と打つと、tasks テーブル全件を表示、tr と打つと task\_records 全件を表示、などとなっている。

とにかく日付関連の項目が全部ミリ秒で保存されているので、コンソールで select 文を手打するのは無理。