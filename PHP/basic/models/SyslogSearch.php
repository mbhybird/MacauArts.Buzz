<?php

namespace app\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use app\models\Syslog;

/**
 * SyslogSearch represents the model behind the search form about `app\models\Syslog`.
 */
class SyslogSearch extends Syslog
{
    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id', 'triggertype'], 'integer'],
            [['userid', 'beaconid', 'logtime'], 'safe'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = Syslog::find();

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        $query->andFilterWhere([
            'id' => $this->id,
            'logtime' => $this->logtime,
            'triggertype' => $this->triggertype,
        ]);

        $query->andFilterWhere(['like', 'userid', $this->userid])
            ->andFilterWhere(['like', 'beaconid', $this->beaconid]);

        return $dataProvider;
    }
}
