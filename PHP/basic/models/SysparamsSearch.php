<?php

namespace app\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use app\models\Sysparams;

/**
 * SysparamsSearch represents the model behind the search form about `app\models\Sysparams`.
 */
class SysparamsSearch extends Sysparams
{
    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id', 'paramkey'], 'integer'],
            [['paramgroup', 'paramvalue'], 'safe'],
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
        $query = Sysparams::find();

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
            'paramkey' => $this->paramkey,
        ]);

        $query->andFilterWhere(['like', 'paramgroup', $this->paramgroup])
            ->andFilterWhere(['like', 'paramvalue', $this->paramvalue]);

        return $dataProvider;
    }
}
